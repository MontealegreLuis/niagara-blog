# Spring Boot Demo Blog

[![MIT License][license-badge]][license]
[![Build Status][travis-badge]][travis]
[![CODEBEAT badge][codebeat-badge]][codebeat]

This is a small demo of a blog using Spring Boot.

# Configuration

To run this application you'll need a MYSQL database user.
You can create a user with the following command.

```sql
CREATE USER blog_user@localhost
  IDENTIFIED BY 'Blog_password1!';

GRANT ALL ON blog_db.* TO blog_user@localhost;
```

You'll also need to create an `application.properties` file. 
Execute the following command to create one:

```bash
$ cp src/main/resources/application.dist.properties src/main/resources/application.properties
```

Update your database credentials in this file, *if needed*.

# Usage

Install the applications dependencies using Maven.

```
$ mvn package -Dmaven.test.skip=true
```

Enjoy!

```
java -jar target/blog-0.0.1-SNAPSHOT.jar
```

[license-badge]: https://img.shields.io/github/license/mashape/apistatus.svg?maxAge=2592000
[license]: LICENSE
[travis-badge]: https://travis-ci.org/MontealegreLuis/spring-blog.svg?branch=master
[travis]: https://travis-ci.org/MontealegreLuis/spring-blog
[codebeat-badge]: https://codebeat.co/badges/cb353c07-f5f0-4862-a7f1-419008e1c40b
[codebeat]: https://codebeat.co/projects/github-com-montealegreluis-spring-blog-master
