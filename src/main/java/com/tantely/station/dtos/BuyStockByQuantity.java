package com.tantely.station.dtos;


import lombok.Data;

import java.io.Serializable;

@Data
public class BuyStockByQuantity implements Serializable {
    private Integer stationId;
    private Integer productId;
    private Double quantity;
}
