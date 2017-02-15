/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.models;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

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
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @Column(length = 2000, nullable = false)
    @NotBlank(message = "Descriptions cannot be empty")
    @Size(min = 5, message = "Description must have at least 5 characters")
    private String description;

    // will define your foreign key
    // i will use a convention  `the_other_table_name_id`
    @ManyToOne
    @JoinColumn (name = "user_id")  // define at the table level
    private User user;  // owner, author

    public Ad(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Ad() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
