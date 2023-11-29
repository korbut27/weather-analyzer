package com.example.weatheranalyzer.repository;

import com.example.weatheranalyzer.domain.weather.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Weather findFirstByOrderByUpdateTimeDesc();

    @Query(value = """
            SELECT * FROM weather
            WHERE update_time BETWEEN :from AND :to
            """, nativeQuery = true)
    List<Weather> findAllWeatherFromRange(@Param("from") LocalDateTime from,
                                          @Param("to") LocalDateTime to);
}
