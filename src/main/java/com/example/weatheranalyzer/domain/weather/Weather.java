package com.example.weatheranalyzer.domain.weather;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "temp_c", nullable = false)
    private double tempC;

    @Column(name = "wind_kph", nullable = false)
    private double windKph;

    @Column(name = "pressure_in", nullable = false)
    private double pressureIn;

    @Column(name = "humidity", nullable = false)
    private double humidity;

    @Column(name = "`condition`", nullable = false)
    private String condition;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;
}