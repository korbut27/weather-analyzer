package com.example.weatheranalyzer.web.dto.date;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Data
public class DateRangeRequest {
    private LocalDateTime from;
    private LocalDateTime to;

    public DateRangeRequest(Date from, Date to){
        this.from = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.to = to.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

    }
}