package com.example.weatheranalyzer.web.dto.date;

import com.example.weatheranalyzer.domain.exception.InvalidDateRangeException;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Data
public class DateRangeRequest {

    private LocalDateTime from;

    private LocalDateTime to;

    public DateRangeRequest(Date from, Date to) throws InvalidDateRangeException {
        this.from = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.to = to.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        if (this.from.isAfter(this.to)) {
            throw new InvalidDateRangeException("'from' date cannot be greater than 'to' date");
        }
    }
}