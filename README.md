# Spring Boot Demo Blog

[![license](https://img.shields.io/github/license/mashape/apistatus.svg?maxAge=2592000)](LICENSE)
[![codebeat badge](https://codebeat.co/badges/cb353c07-f5f0-4862-a7f1-419008e1c40b)](https://codebeat.co/projects/github-com-montealegreluis-spring-blog-master)

This is a small demo of a blog using Spring Boot.

# Configuration

To run this application you'll need a MYSQL database and a user. Use the following queries as a 
guide to create your database and user.

```sql
CREATE USER blog_user@localhost
  IDENTIFIED BY 'Blog_password1!';

GRANT ALL ON blog_db.* TO blog_user@localhost;
```

You'll also need to create an `application.properties` file. Execute the following command to create
one:

```bash
$ cp src/main/resources/application.dist.properties src/main/resources/application.properties
```

Update your database credentials in this file, if needed.

# Installation

1. Install the applications dependencies using Maven.
1. That's it. Enjoy!
