package com.example.weatheranalyzer.web.controller;

import com.example.weatheranalyzer.domain.exception.InvalidDateRangeException;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testGetAverageWeather_ValidDateRange() throws InvalidDateRangeException {
        DateRangeRequest dateRangeRequest = new DateRangeRequest(from, to);

        WeatherDto weatherDto = new WeatherDto();
        Weather weather = new Weather();
        weatherDto.setTempC(20.0);
        weatherDto.setWindKph(8.0);

        Mockito.when(weatherService.getAverageWeather(Mockito.any(), Mockito.any())).thenReturn(weather);

        Mockito.when(weatherMapper.toDto(Mockito.any())).thenReturn(weatherDto);

        ResponseEntity<?> response = weatherController.getAverageWeather(dateRangeRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(weatherDto, response.getBody());
    }

    @Test
    public void testGetAverageWeather_InvalidDateRange() {
        LocalDate fromDate = LocalDate.of(2023, 11, 29);  // Earlier date
        LocalDate toDate = LocalDate.of(2023, 11, 20);    // Later date

        Date from = Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(toDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        try {
            new DateRangeRequest(from, to);
            fail("Expected IllegalArgumentException was not thrown");
        } catch (InvalidDateRangeException ex) {
            assertTrue(ex.getMessage().contains("'from' date cannot be greater than 'to' date"));
        } catch (Exception e) {
            fail("Unexpected exception was thrown: " + e.getClass().getName());
        }
    }
}
