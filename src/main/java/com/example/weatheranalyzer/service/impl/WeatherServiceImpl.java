package com.example.weatheranalyzer.service.impl;

import com.example.weatheranalyzer.domain.weather.Weather;
import com.example.weatheranalyzer.repository.WeatherRepository;
import com.example.weatheranalyzer.service.WeatherService;
import com.example.weatheranalyzer.web.dto.WeatherDto;
import com.example.weatheranalyzer.web.dto.WeatherResponse;
import com.example.weatheranalyzer.web.mapper.WeatherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepository;
    private  final WeatherMapper weatherMapper;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.host}")
    private String apiHost;

    @Value("${weather.api.city}")
    private String city;

    @Override
    @Scheduled(fixedRate = 600000)
    public void getWeather() throws IOException {
        String url = "https://weatherapi-com.p.rapidapi.com/current.json?q=" + city;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", apiKey);
        headers.set("X-RapidAPI-Host", apiHost);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        WeatherDto weatherDto = WeatherResponse.mapFromResponse(response);
        Weather weather = weatherMapper.toEntity(weatherDto);
        weatherRepository.save(weather);
    }

    @Override
    public WeatherDto getActualWeather() {
        return weatherMapper.toDto(weatherRepository.findFirstByOrderByUpdateTimeDesc());
    }

}
