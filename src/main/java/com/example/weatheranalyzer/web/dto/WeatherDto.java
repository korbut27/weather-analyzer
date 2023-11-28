package com.example.weatheranalyzer.web.dto;

import lombok.Data;

@Data
public class WeatherDto {
    private double tempC;

    private double windKph;

    private double pressureIn;

    private double humidity;

    private String condition;

    private String location;

    private String updateTime;
}
