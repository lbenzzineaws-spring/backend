# Rewards Points API - backend By Latif Benzzine

Rewards Points API is a REST API to calculate the amount of points earned per customer, per transaction, per month, per trimester (Three consecutive months) as well as total rewards point earneed by the Customer.

## Technolgies used

The API was built using these technologies:

 - Java 8 or later
- Spring Boot 2.3.4
 - Junit 5
- Mockito
 - Hibernate 5+
- Spring Data JPA
Local in memory H2 db
- maven 3+
- Postman
- Swagger for API Documentation

## Installation

To run the project follow these steps:
  - Clone the repo
- if necessary make chnages in the application.properties file to change the port.
I have it configured to run on port 9001 with the actuator monitoring running on port 9000.

## Documentation

Swagger documentation for the API is available at the URL:
http://localhost:9001/swagger-ui.html (or whatever port number you end up using)



A postman collection can be found here (Although not comprehensive but I gathered all endpoints + few assertions )

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/64811d0f24b02d01e966)



## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## LB
