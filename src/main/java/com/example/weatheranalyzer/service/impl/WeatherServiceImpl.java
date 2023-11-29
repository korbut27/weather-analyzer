package com.example.weatheranalyzer.service.impl;

import com.example.weatheranalyzer.domain.weather.Weather;
import com.example.weatheranalyzer.repository.WeatherRepository;
import com.example.weatheranalyzer.service.WeatherService;
import com.example.weatheranalyzer.web.dto.weather.WeatherDto;
import com.example.weatheranalyzer.web.dto.weather.WeatherResponse;
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
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

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
    @Scheduled(fixedRate = 900000)
    public void getWeather() throws IOException, ParseException {
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

    @Override
    public WeatherDto getAverageWeather(LocalDateTime from, LocalDateTime to) {
        List<Weather> weatherList = weatherRepository.findAllWeatherFromRange(from, to);
        int size = weatherList.size();
        if (size == 0) {
            return new WeatherDto();
        }

        double sumTempC = 0;
        double sumWindKph = 0;
        double sumPressureIn = 0;
        double sumHumidity = 0;

        for (Weather weather : weatherList) {
            sumTempC += weather.getTempC();
            sumWindKph += weather.getWindKph();
            sumPressureIn += weather.getPressureIn();
            sumHumidity += weather.getHumidity();
        }



        double averageTempC = Math.round(sumTempC / size * 100.0) / 100.0;
        double averageWindKph = Math.round(sumWindKph / size * 100.0) / 100.0;
        double averagePressureIn = Math.round(sumPressureIn / size * 100.0) / 100.0;
        double averageHumidity = Math.round(sumHumidity / size * 100.0) / 100.0;

        Weather averageWeather = new Weather();
        averageWeather.setTempC(averageTempC);
        averageWeather.setWindKph(averageWindKph);
        averageWeather.setPressureIn(averagePressureIn);
        averageWeather.setHumidity(averageHumidity);

        return weatherMapper.toDto(averageWeather);
    }

}
