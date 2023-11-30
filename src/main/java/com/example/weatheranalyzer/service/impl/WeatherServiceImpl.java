package com.example.weatheranalyzer.service.impl;

import com.example.weatheranalyzer.domain.exception.WeatherDataException;
import com.example.weatheranalyzer.domain.weather.Weather;
import com.example.weatheranalyzer.repository.WeatherRepository;
import com.example.weatheranalyzer.service.WeatherService;
import com.example.weatheranalyzer.web.dto.weather.WeatherResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private static final Logger LOGGER = LogManager.getLogger(WeatherServiceImpl.class);

    private final WeatherRepository weatherRepository;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.host}")
    private String apiHost;

    @Value("${weather.api.city}")
    private String city;

    @Override
    @Transactional
    @Scheduled(fixedRate = 900000)
    public void getWeather() throws WeatherDataException {
        try {
            String url = "https://weatherapi-com.p.rapidapi.com/current.json?q=" + city;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-RapidAPI-Key", apiKey);
            headers.set("X-RapidAPI-Host", apiHost);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            Weather weather = WeatherResponse.mapFromResponse(response);
            weatherRepository.save(weather);
            LOGGER.debug("Weather saved in the database");
        }catch (Exception e){
            LOGGER.debug("Error when receiving weather data: " + e.getMessage());
            throw new WeatherDataException("Error when receiving weather data: " + e.getMessage());
        }
    }

    @Override
    public Weather getActualWeather() {
        return weatherRepository.findFirstByOrderByUpdateTimeDesc();
    }

    @Override
    public Weather getAverageWeather(LocalDateTime from, LocalDateTime to) {
        List<Weather> weatherList = weatherRepository.findAllWeatherFromRange(from, to);

        if (weatherList.isEmpty()) {
            return new Weather();
        }
        double averageTempC = weatherList.stream().mapToDouble(Weather::getTempC).average().orElse(0);
        double averageWindKph = weatherList.stream().mapToDouble(Weather::getWindKph).average().orElse(0);
        double averagePressureIn = weatherList.stream().mapToDouble(Weather::getPressureIn).average().orElse(0);
        double averageHumidity = weatherList.stream().mapToDouble(Weather::getHumidity).average().orElse(0);

        Weather averageWeather = new Weather();
        averageWeather.setTempC(Math.round(averageTempC * 100.0) / 100.0);
        averageWeather.setWindKph(Math.round(averageWindKph * 100.0) / 100.0);
        averageWeather.setPressureIn(Math.round(averagePressureIn * 100.0) / 100.0);
        averageWeather.setHumidity(Math.round(averageHumidity * 100.0) / 100.0);

        return averageWeather;
    }

}
