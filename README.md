## About The Application

This is a fullstack Java application for managing a list of cities and their images

### Technologies used:
* **Backend:** Java 17 and Spring Boot 3 with Spring Security 6
* **Database:** H2 embedded database with Flyway migration
* **Frontend:** Thymeleaf

## Running The Application

To get a local copy up and running follow these steps from jar or from source code.

### From .jar

1. Download application .jar file from the latest release
    ```
    https://github.com/Delremi/City-List-Application/releases
    ``` 
2. Run the following command-line command (NB! Java version >=17 must be used)
    ```
    java -jar path-to-application-jar
    ```

### From source code

1. Clone the repo
   ```sh
   git clone https://github.com/Delremi/City-List-Application.git
   ```
2. Run the following Class' main method
   ```
   com.delremi.CityListApplication
   ```

## Usage

By default, the application runs on port 8080 and can be accessed in a web browser at
```
http://localhost:8080
```
Application must be logged into at
```
http://localhost:8080/login
```

Running the application registers two default users to the application. 
One user ("editor") has permissions to edit application city entries, the other ("viewer") does not.
The credentials for those users are as follows (username:password):
1. viewer:viewer
2. editor:editor

Display application landing page with list of cities:
```
http://localhost:8080/
```

Editing a city (NB! If logged in with permitted user):
```
http://localhost:8080/edit/<cityId>
```

Logging out:
```
http://localhost:8080/logout
```

## Contact

**Del Remi Liik** | +37253866399 | liik.delremi@gmail.com
