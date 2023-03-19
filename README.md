# Customers API

## _What did I use?_
- Spring boot 2.7.5
- Java 8
- Gradle
- Liquibase 4.3.1
- MySql 8.0.30
- Testcontainers 1.17.3
- Docker 4.15.0
- Swagger 2


## _Important:_
- You will need to create a database, in my case the name of the database is `customers-db` and I access using the `root` user (no password). You can modify this in the `application.properties` file.
- Liquibase will take care of creating all the necessary database structure and also inserting the necessary and test records.
- The API documentation is available via a Swagger page: http://localhost:8080/swagger-ui.html
- You will find a Postman Collection among the project files with many test and example requests.
- The API has an initial implementation of Security and Authentication, through the login endpoint (http://localhost:8080/api/auth/login) a JWT token will be obtained and must be used in the other requests. The API has a predefined user `admin`, with password `admin_customersapi` .
- To run the project: clone the repository, open it with your favorite editor (IntelliJ in my case), build it with Gradle, run it and everything should be fine (make sure you have the database created and Docker installed)


## _TODO:_