package com.example.weatheranalyzer.service;

import com.example.weatheranalyzer.domain.exception.WeatherDataException;
import com.example.weatheranalyzer.domain.weather.Weather;
import com.example.weatheranalyzer.repository.WeatherRepository;
import com.example.weatheranalyzer.service.impl.WeatherServiceImpl;
import com.example.weatheranalyzer.web.dto.weather.WeatherResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {
    @InjectMocks
    private WeatherServiceImpl weatherServiceImpl;

    @Mock
    private WeatherRepository weatherRepository;

    @Mock
    private final RestTemplate restTemplate = mock(RestTemplate.class);

    private final LocalDateTime from = LocalDateTime.of(2023, 11, 29, 0, 0);
    private final LocalDateTime to = LocalDateTime.of(2023, 11, 30, 0, 0);

    private final Weather weather1 = new Weather(1L, -6.0, 11.5, 29.70, 97.0, "", "", from);
    private final Weather weather2 = new Weather(2L, -7.0, 12.5, 30.70, 98.0, "", "", from);
    private final Weather weather3 = new Weather(3L, -8.0, 13.5, 31.70, 99.0, "", "", from);

    @Test
    void getAverageWeatherTest(){

        when(weatherRepository.findAllWeatherFromRange(from, to))
                .thenReturn(List.of(weather1, weather2, weather3));

        Weather averageWeather = weatherServiceImpl.getAverageWeather(from, to);

        assertEquals(-7.0, averageWeather.getTempC());
        assertEquals(12.5, averageWeather.getWindKph());
        assertEquals(30.70, averageWeather.getPressureIn());
        assertEquals(98.0, averageWeather.getHumidity());
    }

    @Test
    void getWeather_SuccessfullyReceivingWeatherDataAndMappingToEntity() throws IOException, ParseException {

        String expectedResponse = new String(Files.readAllBytes(Paths.get("src/test/resources/data/weather_data.json")));

        when(restTemplate.exchange(
                eq("https://weatherapi-com.p.rapidapi.com/current.json?q=MockCity"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        String city = "MockCity";
        String apiKey = "your-api-key";
        String apiHost = "your-api-host";
        String url = "https://weatherapi-com.p.rapidapi.com/current.json?q=" + city;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", apiKey);
        headers.set("X-RapidAPI-Host", apiHost);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        verify(restTemplate).exchange(
                eq("https://weatherapi-com.p.rapidapi.com/current.json?q=MockCity"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());

        Weather weather = WeatherResponse.mapFromResponse(response);

        assertEquals("London", weather.getLocation());
        assertEquals(0, weather.getTempC(), 0.01);
    }

    @Test
    void getWeather_ErrorReceivingWeatherData_ThrowsException() {
        Mockito.lenient().when(restTemplate.exchange(Mockito.anyString(), any(), any(), eq(String.class)))
                .thenThrow(new RestClientException("Simulated error"));

        Assertions.assertThrows(WeatherDataException.class, () -> weatherServiceImpl.getWeather());
    }
}

