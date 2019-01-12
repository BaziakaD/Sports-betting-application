# Sports betting application

Description
--------
User could create wagers. If user wins, player balance will be increased.
Every  wager consist of the list of the outcomes. Outcome represent one of the wager possible result. And every outcome consist of the list of the outcome odd(wager coefficient).

Users work with application by using GUI interface.

If you, like an admin, want to manipulate with data in database you can use REST API. 

REST API provide next functionality: 
- Create event with outcome and odds
- Get list of all events in short form
- Get detailed information about event by event id.
- Save wager result and update player balance(if player wins)
- Get information about player by player id.

Techonologies:
--------
  - Spring Framework( IoC, Data, MVC, Security)
  - Hibernate
  - MySQL
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
You can import collection of the API endpoint specifications to the Postman from the next link:
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
