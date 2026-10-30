# GradeFix Backend

**Student:** Muhire Pacific  
**Student ID:** 27604  
**Project:** GradeFix

## Project Overview

I created this backend application to manage student grades, course information, lecturers, staff rooms, and grade claim workflows. The system is built using Java Spring Boot and PostgreSQL.

## Run the API

To run the project locally, make sure you have the following installed:

- Java 21
- Maven
- PostgreSQL

Create a PostgreSQL database named `grade_fix`. If your local database settings are different, set the following environment variables before starting the application:

After the application starts successfully, the API will be available at `http://localhost:8080`.

Hibernate will automatically create or update the database tables on startup, and the SQL statements will be shown in the console.

## Using Postman

I used the `grade-fix-midterm.postman_collection.json` file to test the endpoints. In Postman, set the `baseUrl` to the project API URL and use the collection to call the requests.

Some save endpoints return a message instead of the saved entity. After saving a record, I use the matching `/all` endpoint to fetch it, copy the generated UUID, and place it in the correct collection variable.

I also use the lecturer Step 0 seed records through the `/save` endpoints as shown in the Postman collection examples.
