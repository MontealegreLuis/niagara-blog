/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.infrastructure.web;

import lombok.Getter;

@Getter
public class FlashMessage {
    private final String type;
    private final String content;

    private FlashMessage(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public static FlashMessage success(String content) {
        return new FlashMessage("success", content);
    }

    public static FlashMessage warning(String content) {
        return new FlashMessage("warning", content);
    }
}
