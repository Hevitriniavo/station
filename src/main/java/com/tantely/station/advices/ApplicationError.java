package com.tantely.station.advices;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class ApplicationError {
    private String message;
    private LocalDate date;
    private int status;
}