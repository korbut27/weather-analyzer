package com.example.weatheranalyzer.repository;

import com.example.weatheranalyzer.domain.weather.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Weather findFirstByOrderByUpdateTimeDesc();
}
