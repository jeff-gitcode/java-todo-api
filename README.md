# Java Todo API

This project is a Todo API built using **Java 23**, **Spring Boot 3.4.3**, and **SQLite**, following the **Clean Architecture** principles and implementing the **CQRS** pattern. It includes **JWT authentication** for secure access and provides a Swagger UI for API documentation and testing.

## Project Structure

```
java-todo-api
├── .gitignore
├── .idea
│   ├── .gitignore
│   ├── compiler.xml
│   ├── encodings.xml
│   ├── httpRequests
│   ├── jarRepositories.xml
│   ├── libraries
│   ├── misc.xml
│   ├── vcs.xml
│   └── workspace.xml
├── .qodo
├── api-client.http
├── doc
│   └── swagger.png
├── lib
│   ├── angus-activation-2.0.0.jar
│   ├── antlr4-runtime-4.13.0.jar
│   ├── apiguardian-api-1.1.2.jar
│   ├── byte-buddy-1.14.15.jar
│   ├── byte-buddy-1.15.0.jar
│   ├── byte-buddy-agent-1.14.15.jar
│   ├── byte-buddy-agent-1.15.0.jar
│   ├── classmate-1.5.1.jar
│   ├── hamcrest-core-1.3.jar
│   └── ...
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           ├── application
│   │   │           │   ├── auth
│   │   │           │   │   ├── JwtRequestFilter.java
│   │   │           │   │   └── JwtUtil.java
│   │   │           │   ├── config
│   │   │           │   │   └── SecurityConfig.java
│   │   │           │   ├── dto
│   │   │           │   │   └── TodoDTO.java
│   │   │           │   ├── interfaces
│   │   │           │   │   ├── TodoRepository.java
│   │   │           │   │   └── UserRepository.java
│   │   │           │   ├── service
│   │   │           │   │   └── TodoService.java
│   │   │           │   └── usecase
│   │   │           │       ├── auth
│   │   │           │       │   ├── SigninUserQueryHandler.java
│   │   │           │       │   └── SignupUserCommandHandler.java
│   │   │           │       ├── command
│   │   │           │       │   ├── CreateTodoCommandHandler.java
│   │   │           │       │   ├── DeleteTodoCommandHandler.java
│   │   │           │       │   └── UpdateTodoCommandHandler.java
│   │   │           │       └── query
│   │   │           │           ├── GetAllTodosQueryHandler.java
│   │   │           │           └── GetTodoByIdQueryHandler.java
│   │   │           ├── domain
│   │   │           │   ├── command
│   │   │           │   │   ├── CreateTodoCommand.java
│   │   │           │   │   ├── DeleteTodoCommand.java
│   │   │           │   │   ├── SignupUserCommand.java
│   │   │           │   │   └── UpdateTodoCommand.java
│   │   │           │   ├── model
│   │   │           │   │   ├── Todo.java
│   │   │           │   │   └── User.java
│   │   │           │   └── query
│   │   │           │       ├── GetAllTodosQuery.java
│   │   │           │       ├── GetTodoByIdQuery.java
│   │   │           │       └── SigninUserQuery.java
│   │   │           ├── presentation
│   │   │           │   └── controller
│   │   │           │       ├── AuthController.java
│   │   │           │       ├── HelloController.java
│   │   │           │       └── TodoController.java
│   │   │           └── App.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── schema.sql
│   └── test
│       └── java
│           └── com
│               └── example
│                   ├── application
│                   │   ├── auth
│                   │   │   ├── JwtRequestFilterUnitTest.java
│                   │   │   └── JwtUtilUnitTest.java
│                   │   ├── service
│                   │   │   └── TodoServiceUnitTest.java
│                   │   └── usecase
│                   │       ├── command
│                   │       │   ├── CreateTodoCommandHandlerUnitTest.java
│                   │       │   ├── DeleteTodoCommandHandlerUnitTest.java
│                   │       │   └── UpdateTodoCommandHandlerUnitTest.java
│                   │       └── query
│                   │           ├── GetAllTodosQueryHandlerUnitTest.java
│                   │           └── GetTodoByIdQueryHandlerUnitTest.java
│                   ├── integration
│                   │   ├── TodoController1IntegrationTest.java
│                   │   ├── TodoControllerIntegrationTest.java
│                   │   └── TodoServiceIntegrationTest.java
│                   └── presentation
│                       └── controller
│                           ├── AuthControllerUnitTest.java
│                           ├── TodoController2UnitTest.java
│                           └── TodoControllerUnitTest.java
├── target
│   ├── classes
│   ├── generated-sources
│   ├── generated-test-sources
│   ├── surefire-reports
│   └── test-classes
└── ...
```

## Features

- Create, retrieve, update, and delete todos.
- Uses SQLite for data persistence.
- Follows Clean Architecture principles for better separation of concerns.
- Implements CQRS for handling commands and queries separately.
- JWT authentication for secure access to the API.
- Swagger UI for API documentation and testing.

## Setup Instructions

1. Clone the repository:
   ```sh
   git clone <repository-url>
   cd java-todo-api
   ```

2. Build the project using Maven:
   ```sh
   mvn clean install
   or
   mvn dependency:purge-local-repository -DreResolve=true
   ```

3. Run the application:
   ```sh
   mvn spring-boot:run
   or
   mvn org.springframework.boot:spring-boot-maven-plugin:run
   ```

4. Access the API at `http://localhost:8080/todos`.

5. Access Swagger UI for API documentation and testing at `http://localhost:8080/swagger-ui/index.html`.

## Setting Up the Database

To set up the SQLite database and create the necessary tables, you can use the provided `schema.sql` script. Follow these steps:

1. Ensure you have SQLite installed on your machine. You can download it from [SQLite Download Page](https://www.sqlite.org/download.html).

2. Navigate to the `src/main/resources` directory where the `schema.sql` file is located.

3. Run the following command to create the database and tables:

   ```sh
   # cmd
   sqlite3 todo.db < schema.sql

   # ps
   Get-Content schema.sql | sqlite3 todo.db
   ```

This command will create a `todo.db` SQLite database file and execute the SQL statements in the `schema.sql` file to create the necessary tables.

## Usage

- **User Signup**: Send a POST request to `/auth/signup` with a JSON body containing `email` and `password`.
- **User Signin**: Send a POST request to `/auth/signin` with a JSON body containing `email` and `password`. The response will include a JWT token.
- **Create a Todo**: Send a POST request to `/todos` with a JSON body containing `title` and the `Authorization: Bearer {token}` header.
- **Get All Todos**: Send a GET request to `/todos` with the `Authorization: Bearer {token}` header.
- **Get a Todo by ID**: Send a GET request to `/todos/{id}` with the `Authorization: Bearer {token}` header.
- **Update a Todo**: Send a PUT request to `/todos/{id}` with the updated JSON body and the `Authorization: Bearer {token}` header.
- **Delete a Todo**: Send a DELETE request to `/todos/{id}` with the `Authorization: Bearer {token}` header.

## Testing the Project

This project includes unit tests and integration tests to ensure the functionality of the application. Follow the steps below to run the tests:

### Prerequisites

- Ensure you have Maven installed and configured.
- Ensure the required Java version (Java 23) is installed.

### Running All Tests

To run all tests (unit and integration tests), use the following Maven command:

```sh
mvn test
```

This will execute all tests in the `src/test/java` directory and generate test reports in the `target/surefire-reports` directory.

### Running Specific Tests

To run a specific test class, use the following command:

```sh
mvn -Dtest=<TestClassName> test
```

For example, to run the `TodoControllerUnitTest`:

```sh
mvn -Dtest=TodoControllerUnitTest test
```

### Viewing Test Reports

After running the tests, you can view the test reports in the `target/surefire-reports` directory. Each test class will have a corresponding `.txt` and `.xml` report file.

### Testing with Coverage

To generate a test coverage report, use the following command:

```sh
mvn clean verify
```

This will generate a coverage report using tools like JaCoCo. The report will be available in the `target/site/jacoco` directory.

### Mocking and Authentication in Tests

- **Mocking Dependencies**: The project uses `@Mock` and `@MockBean` annotations to mock dependencies in unit tests.
- **Authentication**: For controller tests, the `@WithMockUser` annotation is used to simulate an authenticated user.

### Example: Running a Unit Test

Here’s an example of running a unit test for the `TodoService`:

```sh
mvn -Dtest=TodoServiceUnitTest test
```

### Example: Running an Integration Test

Here’s an example of running an integration test for the `TodoController`:

```sh
mvn -Dtest=TodoControllerIntegrationTest test
```

### Debugging Tests

If a test fails, check the detailed logs in the `target/surefire-reports` directory. For example:

```sh
cat target/surefire-reports/com.example.presentation.controller.TodoControllerUnitTest.txt
```

This will show the detailed output of the test execution, including any errors or stack traces.

### Running Tests in an IDE

You can also run tests directly from your IDE (e.g., IntelliJ IDEA or Eclipse):
1. Navigate to the test class in the `src/test/java` directory.
2. Right-click on the test class or method and select "Run" or "Debug".
3. View the results in the IDE's test runner.

### Testing Summary

- **Unit Tests**: Test individual components (e.g., services, handlers).
- **Integration Tests**: Test the interaction between components (e.g., controllers and services).
- **Test Reports**: Available in `target/surefire-reports`.
- **Coverage Reports**: Available in `target/site/jacoco`.

By following these steps, you can ensure the application is thoroughly tested and functioning as expected.

### Docker Instructions

For instructions on how to build and run the application using Docker, refer to the [Docker Guide](README.Docker.md).

## License

This project is licensed under the MIT License. See the LICENSE file for details.