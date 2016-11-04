# Shortener

Prerequisites
---
- Maven
- Java 8
- Mysql
- Redis
- Lombok plugin for IDE of your choice

How to start the Shortener application
---

1. `make package` to build your application
2. `make local` to set up local database with test database
3. `make migrate` to sync database changes
4. `make run` to start the application
5. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`

Dependency Injection
---

We chose to use Guice to do application specific injections.
These injection should be constructor injections.
JAX-RS specific objects should be injected via method or parameter injections.
