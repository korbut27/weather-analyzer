# weather-analyzer

### Project Description "Weather Analyzer"

This project is a weather analyzer developed in accordance with the terms of reference. The application uses WeatherAPI to retrieve weather information and saves it to a database at specified intervals for a specific city.

#### How it works

The application receives information about the weather in Minsk from a WeatherAPI on a schedule and saves it to the database. The city for weather request is always fixed.

#### First endpoint
This endpoint provides the most up-to-date weather information stored in the service's database. The response includes the following details:

Request(/api/v1/weather/actual)

Response
{
    "temperature": -5.0,
    "windSpeedKph": 11.2,
    "pressureInHg": 29.74,
    "humidityPercent": 100.0,
    "weatherCondition": "Mist",
    "location": "Minsk",
    "updateTime": "2023-12-02T13:45:00"
}

#### Second endpoint(/api/v1/weather/average)

This endpoint provides information about the average daily temperature based on the data available in the service. The user can retrieve the information for the specified period.

Request 
{
    "from": "2023-11-29",
    "to": "2023-12-02"
}
Response
{
    "tempC": -6.51,
    "windKph": 18.1,
    "pressureIn": 29.52,
    "humidity": 93.26,
    "condition": null,
    "location": null,
    "updateTime": null
}"

#### Interacting with the application

The user interacts with the application through the REST API.

#### Technical details

- The application is implemented in Java language version 21.
- Spring Boot 3.2.0 and Spring Data JPA are used.
- MySQL is used as a DBMS.
- The application contains quality error handling and logging using log4j2.
- Covering unit tests using JUnit and Mockito.
- Along with the application contains scripts to create the database schema.

#### Interacting with the application

To interact with the application, you need to use the REST API. To get information about the current weather and average daily temperature, you can access the corresponding endpoints.
