# Shortener

Prerequisites
---
- Maven
- Java 8
- Mysql
- Lombok plugin for IDE of your choice

How to start the Shortener application
---

1. Run `mvn clean package` to build your application
2. (First run on dev) `./db_setup.sh`
3. (First run or after schema update) `./shortener.sh db migrate config.yml`
4. Start application with `./shortener.sh server config.yml`
5. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`

Dependency Injection
---

We chose to use Guice to do application specific injections.
These injection should be constructor injections.
JAX-RS specific objects should be injected via method or parameter injections.
