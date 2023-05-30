## About The Application

This is a fullstack Java application for managing multiple users' clients.

### Technologies used:
* **Backend:** Spring Boot with Spring Security
* **Database:** H2 embedded database with Flyway migration
* **Frontend:** Thymeleaf

## Running The Application

To get a local copy up and running follow these steps.

### From source code

1. Clone the repo
   ```sh
   git clone https://github.com/Delremi/spring-thymeleaf-demo-application.git
   ```
2. Run the following Class' main method
   ```
   SpringThymeleafDemoApplication
   ```
   
### From .jar

1. Download latest application .jar file from
    ```
    https://github.com/Delremi/spring-thymeleaf-demo-application/releases
    ``` 
2. Run the following command-line command
    ```
    java -jar path-to-application-jar
    ``` 

## Usage

By default the application runs on port 8080 and can be accessed in a web browser at
```
http://localhost:8080
```
Application must be logged into at
```
http://localhost:8080/login
```

Running the application registers three default users to the application. The credentials for those users are as follows (username:password):
1. user1:pass
2. user2:pass
3. user3:pass

Adding a city:
```
http://localhost:8080/add
```

Editing a city:
```
http://localhost:8080/edit/<userId>
```

Loging out:
```
http://localhost:8080/logout
```

## Contact

**Del Remi Liik** | +37253866399 | liik.delremi@gmail.com
