# Java 23 Todo API

This project is a Todo API built using Java 23, Spring Boot, and SQLite, following the Clean Architecture principles and implementing the CQRS pattern.

## Project Structure

```
java-todo-api
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           ├── application
│   │   │           │   └── TodoService.java
│   │   │           ├── domain
│   │   │           │   ├── model
│   │   │           │   │   └── Todo.java
│   │   │           │   ├── repository
│   │   │           │   │   └── TodoRepository.java
│   │   │           │   └── command
│   │   │           │       └── CreateTodoCommand.java
│   │   │           ├── infrastructure
│   │   │           └── presentation
│   │   │               └── controller
│   │   │                   └── TodoController.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── schema.sql
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── TodoServiceTest.java
├── pom.xml
└── README.md
```

## Features

- Create, retrieve, update, and delete todos.
- Uses SQLite for data persistence.
- Follows Clean Architecture principles for better separation of concerns.
- Implements CQRS for handling commands and queries separately.

## Setup Instructions

1. Clone the repository:
   ```
   git clone <repository-url>
   cd todo-api
   ```

2. Build the project using Maven:
   ```
   mvn clean install
   or
   mvn dependency:purge-local-repository -DreResolve=true
   ```

3. Run the application:
   ```
   mvn spring-boot:run
   or
   mvn org.springframework.boot:spring-boot-maven-plugin:run
   ```

4. Access the API at `http://localhost:8080/todos`.

5. Access Swagger UI for API documentation and testing at `http://localhost:8080/swagger-ui/index.html`.

<!-- 1. Clone the repository:
   ```
   git clone <repository-url>
   cd java23-todo-api
   ```

2. Build the project using Gradle:

- Create Gradle Wrapper - gradlew.bat
   ```
   gradle wrapper
   ```
   
- On Unix-based systems:
  ```
  ./gradlew build
  ```
- On Windows:
  ```
  gradlew.bat build
  ``` -->

3. Run the application:
- On Unix-based systems:
  ```
  ./gradlew bootRun
  ```
- On Windows:
  ```
  gradlew.bat bootRun
  ```

4. Access the API at `http://localhost:8080/todos`.

## Usage

- **Create a Todo**: Send a POST request to `/todos` with a JSON body containing `title` and `description`.
- **Get All Todos**: Send a GET request to `/todos`.
- **Get a Todo by ID**: Send a GET request to `/todos/{id}`.
- **Update a Todo**: Send a PUT request to `/todos/{id}` with the updated JSON body.
- **Delete a Todo**: Send a DELETE request to `/todos/{id}`.

## License

This project is licensed under the MIT License. See the LICENSE file for details.