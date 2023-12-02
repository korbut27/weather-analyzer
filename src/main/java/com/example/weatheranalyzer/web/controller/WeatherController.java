package com.example.weatheranalyzer.web.controller;

import com.example.weatheranalyzer.service.WeatherService;
import com.example.weatheranalyzer.web.dto.date.DateRangeRequest;
import com.example.weatheranalyzer.web.dto.weather.WeatherDto;
import com.example.weatheranalyzer.web.mapper.WeatherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    private final WeatherMapper weatherMapper;

    @GetMapping("/actual")
    public ResponseEntity<WeatherDto> getActualWeather() {
        return ResponseEntity.ok().body(weatherMapper.toDto(weatherService.getActualWeather()));
    }


    @GetMapping("/average")
    public ResponseEntity<?> getAverageWeather(@RequestBody DateRangeRequest dateRangeRequest) {
        return ResponseEntity.ok().body(weatherMapper
                .toDto(weatherService.getAverageWeather(dateRangeRequest.getFrom(), dateRangeRequest.getTo())));
    }

}
