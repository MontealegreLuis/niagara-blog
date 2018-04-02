/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.blog;

import com.codeup.infrastructure.events.Event;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor @Setter
public class ImageWasUploaded implements Event, Serializable {
    private final String name = "ImageWasUploaded";
    private String imageFilename;
    private Date occurredOn;

    public ImageWasUploaded(String imageFilename, Date occurredOn) {
        this.imageFilename = imageFilename;
        this.occurredOn = occurredOn;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Date getOccurredOn() {
        return occurredOn;
    }
}
