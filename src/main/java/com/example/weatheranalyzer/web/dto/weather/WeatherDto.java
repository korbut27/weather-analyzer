package com.example.weatheranalyzer.web.dto.weather;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WeatherDto {

    private double tempC;

    private double windKph;

    private double pressureIn;

    private double humidity;

    private String condition;

    private String location;

    private LocalDateTime updateTime;
}
