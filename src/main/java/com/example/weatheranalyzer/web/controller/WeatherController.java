package com.example.weatheranalyzer.web.controller;

import com.example.weatheranalyzer.service.WeatherService;
import com.example.weatheranalyzer.web.dto.WeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/actual")
    public WeatherDto getActualWeather(){
        return weatherService.getActualWeather();
    }


}
