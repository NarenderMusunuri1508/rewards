# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.5/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#data.sql.jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Local Setup
1. Clone project in to your IDE
2. Run RewardsApplication.java as Java Application
3. All database tables will be created and populated with sample data on the startup
4. you can test api using below API
   ```
   POST: http://localhost:8989/customer/{id}/rewards 
   example URL: http://localhost:8989/customer/1001/rewards
   ```
   example POST body: 
  ```json
   {
     "rewardNames":["SPEND_OVER_100", "SPEND_BETWEEN_50_100"],
     "rewardsForNoOfMonths": 3
   }
  ```
  example Response
  ```json
   {
    "error": null,
    "data": {
        "customerId": 1001,
        "rewardsByMonth": {
            "NOVEMBER": 375,
            "SEPTEMBER": 245,
            "OCTOBER": 150
        },
        "totalRewards": 770
    }
 }
 ```
5. You can view h2 console using http://localhost:8989/h2-console/login.do
