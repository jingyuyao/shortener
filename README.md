# CMS

How to start the CMS application
---

1. Run `mvn clean package` to build your application
1. Start application with `./cms.sh server`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`

Dependency Injection
---

We chose to use Guice to do application specific injections.
These injection should be constructor injections.
JAX-RS specific objects should be injected via method or parameter injections.
