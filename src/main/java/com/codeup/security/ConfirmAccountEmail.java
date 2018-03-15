/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConfirmAccountEmail {
    private static final Log log = LogFactory.getLog(ConfirmAccountEmail.class);

    private JavaMailSender sender;

    public ConfirmAccountEmail(JavaMailSender sender) {
        this.sender = sender;
    }

    public void receive(String event) throws IOException {
        sendEmail(parse(event));

        log.info("Received the following message " + event);
    }

    private UserSignedUp parse(String event) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(event, UserSignedUp.class);
    }

    private void sendEmail(UserSignedUp event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.getEmail());
        message.setSubject(String.format("Welcome %s!", event.getUsername()));
        message.setText("You can now start publishing posts");
        sender.send(message);
    }

}
