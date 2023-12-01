package com.example.weatheranalyzer.web.controller;

import com.example.weatheranalyzer.domain.weather.Weather;
import com.example.weatheranalyzer.service.impl.WeatherServiceImpl;
import com.example.weatheranalyzer.web.dto.date.DateRangeRequest;
import com.example.weatheranalyzer.web.dto.weather.WeatherDto;
import com.example.weatheranalyzer.web.mapper.WeatherMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class WeatherControllerTest {

    @Mock
    private WeatherServiceImpl weatherService;

    @Mock
    private WeatherMapper weatherMapper;

    @InjectMocks
    private WeatherController weatherController;

    private final Date from = new Date();
    private final Date to = new Date();

    @Test
    public void testGetActualWeather() {
        WeatherDto weatherDto = new WeatherDto();
        Weather weather = new Weather();
        weatherDto.setTempC(25.0);
        weatherDto.setWindKph(10.0);

        Mockito.when(weatherService.getActualWeather()).thenReturn(weather);

        Mockito.when(weatherMapper.toDto(Mockito.any())).thenReturn(weatherDto);

        ResponseEntity<WeatherDto> response = weatherController.getActualWeather();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(weatherDto, response.getBody());
    }

//    @Test
//    public void testGetAverageWeather_ValidDateRange() throws ParseException {
//        DateRangeRequest dateRangeRequest = new DateRangeRequest(from, to);
//
//        WeatherDto weatherDto = new WeatherDto();
//        Weather weather = new Weather();
//        weatherDto.setTempC(20.0);
//        weatherDto.setWindKph(8.0);
//
//        Mockito.when(weatherService.getAverageWeather(Mockito.any(), Mockito.any())).thenReturn(weather);
//
//        Mockito.when(weatherMapper.toDto(Mockito.any())).thenReturn(weatherDto);
//
//        ResponseEntity<?> response = weatherController.getAverageWeather(dateRangeRequest);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(weatherDto, response.getBody());
//    }

//    @Test
//    public void testGetAverageWeather_InvalidDateRange() {
//        // Create a DateRangeRequest instance with invalid date range
//        DateRangeRequest dateRangeRequest = new DateRangeRequest();
//
//        // Mock the behavior of weatherService.getAverageWeather() to throw IllegalArgumentException
//
//        // Call the controller method
//        ResponseEntity<?> response = weatherController.getAverageWeather(dateRangeRequest);
//
//        // Assert the response
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("Invalid date range: <error message>", response.getBody());
//    }
}
