package com.example.weatheranalyzer.service;

import com.example.weatheranalyzer.domain.exception.WeatherDataException;
import com.example.weatheranalyzer.domain.weather.Weather;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;

public interface WeatherService {
    void getWeather() throws IOException, ParseException, WeatherDataException;

    Weather getActualWeather();

    Weather getAverageWeather(LocalDateTime from, LocalDateTime to);
}
