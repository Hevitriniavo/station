package com.tantely.station.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class DailySaleRequest {
    private LocalDate from;
    private LocalDate to;
}
