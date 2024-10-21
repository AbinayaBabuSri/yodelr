# Yodelr - a micro-message communications platform

This project provides a REST API for a micro-messaging platform called Yodelr. It is built using Java Spring Boot and testable via Swagger UI. 

### Prerequisites

- Java 17
- Maven 3.9.6 or higher

### Installation

1. Download or Clone the repository.
2. Compile the project using Maven from project directory:
	- mvn clean install
	
### Running the Application
Import Project as Maven project in IDE and run as Java application.

Or

Once everything is set up, you can run the Spring Boot application using Maven: mvn spring-boot:run
	
Or

You can also run the generated JAR file(which will be located in the target/ directory) as :
java -jar target/yodelr-0.0.1-SNAPSHOT.jar

### API Acessibility
 The base URL for the API will be: http://localhost:8080/api/yodelr

 Swagger UI can be accessed at: http://localhost:8080/swagger-ui.html
 
### API Details

1. Add a user : Adds a user with the given username to the system.

2. Add a Post : Adds a post for the given user with the given post text and timestamp. 

3. Delete a User : Deletes the given user and all posts made by the user from the system.

4. Get Posts For a User : Returns a list of all post texts made by the given user.

5. Get Posts For a Topic : Returns a list of all post texts containing the given topic.

6. Get Trending Topics : Returns a list of all unique topics mentioned in posts made in the given time interval.

