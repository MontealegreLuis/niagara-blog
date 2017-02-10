/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.models;

import javax.persistence.*;

// Hibernate

// select * from ads where id = 1   -> ResultSet
// .next
// new Ad()  -> returning

// generic
// Ad ad = new Ad();   //calls the default constructor
// only using the setters
// ad.setInt(rs.nextInt();
// ad.setTitle(rs.getString());
// ad.setDescription(rs.getString());
// return ad;


@Entity
@Table(name="ads")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
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

    public int getId() {
        return id;
    }
}
