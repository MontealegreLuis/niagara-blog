/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.infrastructure.events;

public interface CanRecordEvents {
    void recordThat(Event event);

    Iterable<Event> events();
}
