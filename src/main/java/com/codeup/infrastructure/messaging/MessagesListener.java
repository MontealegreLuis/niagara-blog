/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.infrastructure.messaging;

import com.codeup.infrastructure.events.EventSubscriber;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessagesListener {
    private static final Log log = LogFactory.getLog(MessagesListener.class);

    private List<EventSubscriber> subscribers;

    public MessagesListener() {
        this.subscribers = new ArrayList<>();
    }

    public void attach(EventSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void receive(String message) throws IOException {
        String name = parse(message).get("name");
        for (EventSubscriber subscriber : subscribers) {
            processMessage(message, name, subscriber);
        }
    }

    private void processMessage(String message, String name, EventSubscriber subscriber) {
        try {
            if (subscriber.isSubscribedTo(name)) subscriber.handle(subscriber.parse(message));
        } catch (Exception exception) {
            log.info(String.format(
                "%s failed to handle the following message: %s",
                subscriber.getClass().getName(),
                message
            ));
            log.error(exception);
        }
    }

    private Map<String, String> parse(String message) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(message, new TypeReference<Map<String, String>>(){});
    }
}
