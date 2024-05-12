package com.tantely.station.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tantely.station.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class FuelTransactionInfo implements Serializable {
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime transactionDateTime;
        String fuelType;
        TransactionType transactionType;
        Double quantity;
}