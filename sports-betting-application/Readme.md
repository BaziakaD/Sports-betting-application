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

# Users

  - Admin (email@mail.com, 12345678)

### Installation
Install the dependencies and devDependencies and start the server.

```sh
$ cd sports-betting-application\
$ mvn clean package
$ java -jar sports-betting-application-app\target\sports-betting-application-jar-with-dependencies.jar

```
### Docker
You can use docker-compose for create mysql container.

```sh
$ cd Movie-Theater
$ docker-compose up -d
```

### Kubernetes
You can k8s specs to deploy to the k8s cluster

```sh
$ cd sports-betting-application\deploy
$ kubectl apply -f svc.yaml deploy.yaml
```