package com.example.weatheranalyzer.service;

import com.example.weatheranalyzer.web.dto.weather.WeatherDto;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

public interface WeatherService {
    void getWeather() throws IOException, ParseException;

    WeatherDto getActualWeather();

    WeatherDto getAverageWeather(LocalDateTime from, LocalDateTime to);
}
