### Built With

This application was built using the Spring Framework and Spring Boot. It creates a simple web servlet with a REST api endpoint to call

* [Spring](https://spring.io/)

### Building the code

You should have your local environment set to run and build projects using Maven and Java.
* maven build jar
  ```sh
  mvn clean install
  ```

### Running the code

Once you have built the project, you should have a runnable jar in a /target folder.

App will be running with Spring Boot v2.7.0 and Spring v5.3.20

* run jar with Java
   ```sh
   java -jar target/demoApp-0.0.1-SNAPSHOT.jar --spring.profiles.active=local
   ```

<!-- USAGE EXAMPLES -->
## Usage

Basic REST interface that can handle JSON, XML or x-www-form-urlencoded requests, runs validation, and returns a response

### Request XML

`POST http://localhost:8080/demoAPI/v1/request`

    `curl --location --request POST 'http://localhost:8080/demoAPI/v1/request' \
    --header 'Content-Type: application/xml' \
    --data-raw '<userRequest>
      <messageId>12345</messageId>
        <user>
          <firstName>Jane</firstName>
          <lastName>Doe</lastName>
          <userId>01</userId>
      </user>
    </userRequest>'`

### Request JSON

`POST http://localhost:8080/demoAPI/v1/request`

    `curl --location --request POST 'http://localhost:8080/demoAPI/v1/request' \
    --header 'Content-Type: application/json' \
    --data-raw '{
      "messageId" : "12345",
      "user" : {
        "firstName" : "Jane",
        "lastName" : "Doe",
        "userId" : "01"
      }
    }'`

### Request x-www-formurl-encoded

`POST http://localhost:8080/demoAPI/v1/request`

    `curl --location --request POST 'http://localhost:8080/demoAPI/v1/request' \
      --header 'Content-Type: application/x-www-form-urlencoded' \
      --data-urlencode 'messageId=12345' \
      --data-urlencode 'firstName=Jane' \
      --data-urlencode 'lastName=Doe' \
      --data-urlencode 'userId=01'`

### Request String custom deserializer parses string and converts to UserRequest Object

`POST http://localhost:8080/demoAPI/v2/request`

    `curl --location --request POST 'http://localhost:8080/demoAPI/v1/request' \
    --header 'Content-Type: application/json' \
    --data-raw '{
      "messageId" : "12345",
      "user" : {
        "firstName" : "Jane",
        "lastName" : "Doe",
        "userId" : "01"
      }
    }'`

### 200 OK Response

    HTTP/1.1 200 OK
    Content-Type: application/json

    {"messageId": "12345", "userId": "01" }

### 400 Bad Request Response 
#### Sample when required field is missing

    HTTP/1.1 200 OK
    Content-Type: application/json

    {
      "errorCode": "400",
      "errorDescription": "Bad Request",
      "errorMessage": "Fix all errors in request before retrying",
      "validationErrors": [
        {
            "errorMessage": "userId is a required field and cannot be blank",
            "field": "user.userId"
        }
      ]
    }

<p align="right">(<a href="#top">back to top</a>)</p>

