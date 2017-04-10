/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup;

import com.codeup.services.UserDetailsLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsLoader userDetails;

    @Autowired
    public SecurityConfiguration(UserDetailsLoader userDetails) {
        this.userDetails = userDetails;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/posts") // user's home page
                .permitAll() // Anyone can go to the login page
            .and()
                // non logged in users
                .authorizeRequests()
                // Allow without login
                .antMatchers("/", "/posts/{id}", "/posts/image/**", "/logout")
                .permitAll()
            .and()
                .logout()
                .logoutSuccessUrl("/login?logout")
            .and()
                // restricted area
                .authorizeRequests()
                .antMatchers("/posts/{id}/**") // only authenticated users can manage posts
                .authenticated()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails).passwordEncoder(passwordEncoder());
    }
}
