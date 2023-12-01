package com.example.weatheranalyzer.repository;

import com.example.weatheranalyzer.domain.weather.Weather;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherRepositoryTest {
    @Mock
    private WeatherRepository weatherRepository;

    @Test
    public void testFindFirstByOrderByUpdateTimeDesc() {
        Weather expectedWeather = new Weather();
        expectedWeather.setTempC(25.0);
        expectedWeather.setWindKph(10.0);

        when(weatherRepository.findFirstByOrderByUpdateTimeDesc()).thenReturn(expectedWeather);

        Weather actualWeather = weatherRepository.findFirstByOrderByUpdateTimeDesc();
        assertEquals(expectedWeather, actualWeather);
    }

    @Test
    public void testFindAllWeatherFromRange() {
        LocalDateTime from = LocalDateTime.now().minusDays(7);
        LocalDateTime to = LocalDateTime.now();

        List<Weather> expectedWeatherList = IntStream.range(0, 2)
                .mapToObj(i -> {
                    Weather weather = new Weather();
                    weather.setTempC(25.0);
                    weather.setWindKph(10.0);
                    return weather;
                })
                .collect(Collectors.toList());

        when(weatherRepository.findAllWeatherFromRange(from, to)).thenReturn(expectedWeatherList);

        List<Weather> actualWeatherList = weatherRepository.findAllWeatherFromRange(from, to);
        assertEquals(expectedWeatherList, actualWeatherList);
    }
}
