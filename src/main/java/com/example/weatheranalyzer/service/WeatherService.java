package com.example.weatheranalyzer.service;

import com.example.weatheranalyzer.web.dto.WeatherDto;

import java.io.IOException;

public interface WeatherService {
    void getWeather() throws IOException;

    WeatherDto getActualWeather();
}
