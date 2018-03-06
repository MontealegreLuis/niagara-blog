/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.blog;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@ContextConfiguration(classes = {LocalValidatorFactoryBean.class})
public class PostTest {

    @Test
    public void it_can_be_published()
    {
        String title = "New post";
        String body = "Some content";
        PostInformation information = new PostInformation(title, body);
        User author = new User();

        Post post = Post.publish(information, author);

        assertThat(post.getTitle(), equalTo(title));
        assertThat(post.getBody(), equalTo(body));
    }

    @Test
    public void it_cannot_be_published_without_a_title()
    {
        PostInformation information = new PostInformation(null, "Some content");

        Set<ConstraintViolation<PostInformation>> violations = validator.validate(information);

        assertThat(violations.isEmpty(), is(false));
    }

    @Test
    public void it_cannot_be_published_without_a_body()
    {
        PostInformation information = new PostInformation("A title", null);

        Set<ConstraintViolation<PostInformation>> violations = validator.validate(information);

        assertThat(violations.isEmpty(), is(false));
    }

    private static Validator validator;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void setUpClass() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
}
