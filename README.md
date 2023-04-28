This project was generated with Spring Initializr.

**Prerequisites**
To run this project, you will need to have the following installed:

Java 11
Maven 3.x

**Running the Application:** To run the application,

1. Build the application:
execute the mvn clean install command in the project directory.
2. Run the application:
execute the mvn spring-boot:run command in the project directory.
3. http://localhost:8080/index

This will start the application on http://localhost:8080

**Endpoints**
The following endpoints are available:

GET /resource/id/{id} - Retrieves a list of a specific user.

GET /resource/searchbycreateddate - Retrieves a user by createdDate range.

GET /resource/profession/{profession} - Retrieves a user by profession.

POST /resource/search - Search a user by id, email, first name, last name, profession and date range.

