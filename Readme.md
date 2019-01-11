# Sports betting application

Developed with Spring MVC, Spring Security and Spring Data.

Techonologies:
  - Spring Framework( IoC, Data, MVC, Security)
  - Hibernate
  - MySQL
  - Log4j2
  - Jetty
  - Docker 
  - Google Cloud Platform (Google Kubernetes Engine & Cloud SQL)
  
Before you get started
--------
This project targets Java 11. Make sure you have done following:
- Install OpenJDK 11
- Update your IDE. Check if it supports Java 11 (or at least Java 10)
- Update your Maven installation
- Make sure to set target bytecode version, project SDK and supported language level in your IDE to 
Java 11
- Deploy MySQL server or MySQL proxy to the localhost:3306

### REST API request collections
```
https://www.getpostman.com/collections/df21fa2b28d0e534456d
```

### Users
  - User (email@mail.com, 12345678)

### Installation
```sh
$ mvn clean package
$ java -jar sports-betting-application-app\target\sports-betting-application-jar-with-dependencies.jar

```
### Docker
You can use docker-compose if you want to use mysql container.

```sh
$ docker-compose up -d
```

### Kubernetes
You can k8s specs to deploy to the k8s cluster

```sh
$ cd deploy\
$ kubectl apply -f svc.yaml deploy.yaml
```
