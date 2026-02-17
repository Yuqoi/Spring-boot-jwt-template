# Spring Boot JWT Template

[![Java CI with Maven](https://github.com/Yuqoi/Spring-boot-jwt-template/actions/workflows/maven.yml/badge.svg?branch=main)](https://github.com/Yuqoi/Spring-boot-jwt-template/actions/workflows/maven.yml)

Simple jwt authentication spring boot template, automatically generates tables inside postgresql. Also added refresh token to expand better user experience

---

## Uses:

- Java 21
- Spring Boot 4.x.x
- PostgreSQL

---

## Prerequisites

You have to have installed:

- Java 21
- Maven (or use the included mvnw wrapper)
- Docker & Docker Compose

---

## Running the Project

### 1. Clone the repository

```bash
git clone https://github.com/Yuqoi/Spring-boot-jwt-template.git
cd Spring-boot-jwt-template
```

> [!WARNING]  
> Change in `application.yaml` **url** to your credentials

```
driver-class-name: org.postgresql.Driver
url: jdbc:postgresql://<address>:<port>/<db_name>
username: ${DB_USERNAME}
password: ${DB_PASSWORD}
name: jwt
```

Run the application

```bash
mvn spring-boot:run
```

Or run the tests

```bash
mvn test
```
