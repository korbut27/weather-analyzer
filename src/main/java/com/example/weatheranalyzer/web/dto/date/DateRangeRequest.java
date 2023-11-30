package com.example.weatheranalyzer.web.dto.date;

import com.example.weatheranalyzer.service.impl.WeatherServiceImpl;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Data
public class DateRangeRequest {

    private static final Logger LOGGER = LogManager.getLogger(WeatherServiceImpl.class);

    private LocalDateTime from;

    private LocalDateTime to;

    public DateRangeRequest(Date from, Date to){
        checkFormat(from, to);
        this.from = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.to = to.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

    }


    public static void checkFormat(Date from, Date to) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        String fromDate = dateFormat.format(from);
        String toDate = dateFormat.format(to);

        try {
            dateFormat.parse(fromDate);
            dateFormat.parse(toDate);
        } catch (Exception e) {
            LOGGER.debug("Invalid date format. Please use the DD-MM-YYYY format.");
            throw new IllegalArgumentException("Invalid date format. Please use the DD-MM-YYYY format.");
        }
    }
}