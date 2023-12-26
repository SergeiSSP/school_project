# Spring Boot and Spring JDBC Project

## Overview

This project is a Java application built using Spring Boot and Spring JDBC, demonstrating modern best practices in Java development. It leverages Lombok for reducing boilerplate code, TestContainers for integration testing with real databases in a Docker environment, and JUnit 5 for unit testing.

## Features

- **Spring Boot**: Simplifies bootstrapping and development of new Spring applications.
- **Spring JDBC**: Handles database operations using Spring's JDBC abstraction.
- **Lombok**: Reduces boilerplate code like getters, setters, and constructors.
- **TestContainers**: Provides lightweight, throwaway instances of databases for testing purposes.
- **JUnit 5**: Enables unit testing with advanced features and easy-to-write test cases.

## Requirements

- Java JDK 11 or above.
- Maven 3.6 or above.
- Docker for running TestContainers.

## Setup and Installation

1. **Clone the Repository**: `git clone https://github.com/username/spring-jdbc-project.git`
2. **Navigate to the Project Directory**: `cd spring-jdbc-project`
3. **Build the Project**: `mvn clean install`

## Running the Application

- **Run the Application**: `mvn spring-boot:run`

## Running Tests

- **Run Unit Tests**: `mvn test`
- **Integration Tests with TestContainers**: Ensure Docker is running, then execute `mvn verify`.

## How It Works

The application uses Spring JDBC to interact with the database. Lombok is used to minimize boilerplate code in models and data objects. TestContainers are utilized for spinning up database instances during integration testing, ensuring tests run against a real database environment.

