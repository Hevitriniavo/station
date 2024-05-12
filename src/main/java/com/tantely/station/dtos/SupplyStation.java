package com.tantely.station.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class SupplyStation implements Serializable {
    private Integer productId;
    private Integer stationId;
    private Double quantity;
}
