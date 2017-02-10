/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.models;

// bean
public class Ad {
    private long id;
    private String title;
    private String description;

    public Ad(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Ad() {
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }
}
