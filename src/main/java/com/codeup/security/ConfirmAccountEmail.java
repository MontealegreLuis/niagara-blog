/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.security;

import com.codeup.infrastructure.events.Event;
import com.codeup.infrastructure.events.EventSubscriber;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConfirmAccountEmail implements EventSubscriber {
    private static final Log log = LogFactory.getLog(ConfirmAccountEmail.class);

    private JavaMailSender sender;

    public ConfirmAccountEmail(JavaMailSender sender) {
        this.sender = sender;
    }

    @Override
    public boolean isSubscribedTo(String eventName) {
        return "UserSignedUp".equals(eventName);
    }

    @Override
    public UserSignedUp parse(String message) {
        log.info("Processing message: " + message);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(message, UserSignedUp.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handle(Event event) {
        sendEmail((UserSignedUp) event);
    }

    private void sendEmail(UserSignedUp event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.getEmail());
        message.setSubject(String.format("Welcome %s!", event.getUsername()));
        message.setText("You can now start publishing posts");
        sender.send(message);
    }
}
