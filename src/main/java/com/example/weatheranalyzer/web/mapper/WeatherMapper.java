package com.example.weatheranalyzer.web.mapper;

import com.example.weatheranalyzer.domain.weather.Weather;
import com.example.weatheranalyzer.web.dto.weather.WeatherDto;
import org.mapstruct.Mapper;

import java.text.ParseException;

@Mapper(componentModel = "spring")
public interface WeatherMapper {
    Weather toEntity(WeatherDto dto) throws ParseException;

    WeatherDto toDto(Weather weather);
}
