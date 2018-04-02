/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.infrastructure.events;

public interface EventSubscriber {
    boolean isSubscribedTo(String eventName);

    Event parse(String message);

    void handle(Event event);
}
