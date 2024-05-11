package com.tantely.station.entities;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Stock implements Serializable {
    private Integer id;
    private Integer stationId;
    private Integer productId;
    private Double quantity;
    private Double evaporationRate;
}
