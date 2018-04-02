/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.blog;

import com.codeup.infrastructure.events.Event;
import com.codeup.infrastructure.events.EventSubscriber;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.im4java.core.IM4JavaException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ResizeImage implements EventSubscriber {
    private static final Log log = LogFactory.getLog(ResizeImage.class);

    private ImageResizer resizer;

    public ResizeImage(ImageResizer resizer) {
        this.resizer = resizer;
    }

    @Override
    public boolean isSubscribedTo(String eventName) {
        return "ImageWasUploaded".equals(eventName);
    }

    @Override
    public Event parse(String message) {
        log.info("Processing message: " + message);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(message, ImageWasUploaded.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handle(Event event) {
        try {
            resizer.resize(((ImageWasUploaded) event).getImageFilename());
        } catch (IOException | IM4JavaException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
