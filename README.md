# Drinkkiluostari

Welcome to my Spring Boot project! It is intended to be used in a bar to sell
drinks, courses, events, and more, even though its current version is rather
rudimentary. It was created for the Backend course at Haaga-Helia in the fall
of 2024.

## About

This is a Spring Boot project using Thymeleaf. Its basic architecture contains
entity classes, repository classes and DTOs; REST and web controllers using basic
CRUD functionalities; .css styles and Thymeleaf templates; and JUnit tests. It
features an external database (PostgreSQL) and is deployed using Render.

This project uses basic auth Spring Security and has a login feature (see main
app for details). The USER role allows all GET requests and, in addition, POST
requests for Asiakas and Tilaus objects. The ADMIN role allows for the previous
and, in addition, for POST, PUT, and DELETE requests for all objects. DELETE
requests are implemented using the Soft Delete logic, i.e., objects that are
deleted do not show up in requests made by the user but can still be accessed
in the database.

Requests made to this project are validated using Jakarta Bean Validation. It
contains several features outside the scope of the Backend course, namely: the
Soft Delete implementation; DTO classes; the usage of ResponseEntities in the
REST controllers; and custom .css styling.
