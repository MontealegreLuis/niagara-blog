/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.Session;
import java.io.Serializable;

@Service
public class Publisher {
    @Value("${blog.messaging.topic}")
    private String topic;

    private JmsTemplate publisher;

    public Publisher(JmsTemplate publisher) {
        this.publisher = publisher;
    }

    public void publish(Iterable<Event> events) {
        for (Event event : events) publishEvent(event);
    }

    private void publishEvent(Event event) {
        MessageCreator messageCreator = (Session session) -> {
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
            Serializable message;
            try {
                message = writer.writeValueAsString(event);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return session.createObjectMessage(message);
        };

        publisher.send(topic, messageCreator);
    }
}
