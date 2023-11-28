package com.example.weatheranalyzer.web.mapper;

import com.example.weatheranalyzer.domain.weather.Weather;
import com.example.weatheranalyzer.web.dto.WeatherDto;
import com.example.weatheranalyzer.web.dto.WeatherResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WeatherMapper {
    Weather toEntity(WeatherDto dto);

    WeatherDto toDto(Weather weather);
}
