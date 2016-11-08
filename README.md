# Shortener

Prerequisites
---
- Maven (latest)
- Java 8
- Mysql 5.7
- Redis (assumes no local authentication)
- Lombok plugin for IDE of your choice
- httpie (optional)

How to start the Shortener application
---

1. `make package` to build your application
2. `make local` to set up local database with test database
3. `make migrate` to sync database changes
4. `make run` to start the application

Using the application (with httpie)
---

- `http localhost:8080/` for a list of shortened urls
- `http localhost:8080/ url="http://new.url` to create a new shortened link
- `http delete localhost:8080/ id=a` to delete link with the id 'a'
- `http localhost:8080/a` to be redirected to the url pointed to by the shortened link 'a'
- `http localhost:8081/healthcheck` to see a list of enabled health checks

Dependency Injection
---

We chose to use Guice to do application specific injections.
These injection should be constructor injections.
JAX-RS specific objects should be injected via method or parameter injections.
