package com.example.weatheranalyzer.web.dto.weather;

import com.example.weatheranalyzer.deserialization.LocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class WeatherResponse {

    private Location location;
    private Current current;

    public static WeatherDto mapFromResponse(ResponseEntity<String> response) throws IOException, ParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        objectMapper.registerModule(javaTimeModule);
        WeatherResponse weatherResponse = objectMapper.readValue(response.getBody(), WeatherResponse.class);
        return getWeatherDto(weatherResponse);
    }

    private static WeatherDto getWeatherDto(WeatherResponse weatherResponse) {
        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setTempC(weatherResponse.getCurrent().getTemp_c());
        weatherDto.setWindKph(weatherResponse.getCurrent().getWind_kph());
        weatherDto.setPressureIn(weatherResponse.getCurrent().getPressure_in());
        weatherDto.setHumidity(weatherResponse.getCurrent().getHumidity());
        weatherDto.setCondition(weatherResponse.getCurrent().getCondition().getText());
        weatherDto.setLocation(weatherResponse.getLocation().getName());
        weatherDto.setUpdateTime(weatherResponse.getCurrent().getLast_updated());
        return weatherDto;
    }

    @Data
    public static class Location {
        private String name;
        private String region;
        private String country;
        private double lat;
        private double lon;
        private String tz_id;
        private long localtime_epoch;
        private String localtime;
    }

    @Data
    public static class Current {
        private long last_updated_epoch;
        private LocalDateTime last_updated;
        private double temp_c;
        private double temp_f;
        private int is_day;
        private Condition condition;
        private double wind_mph;
        private double wind_kph;
        private int wind_degree;
        private String wind_dir;
        private double pressure_mb;
        private double pressure_in;
        private double precip_mm;
        private double precip_in;
        private double humidity;
        private double cloud;
        private double feelslike_c;
        private double feelslike_f;
        private double vis_km;
        private double vis_miles;
        private double uv;
        private double gust_mph;
        private double gust_kph;
    }

    @Data
    public static class Condition {
        private String text;
        private String icon;
        private int code;
    }


}