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
public class Product implements Serializable {
    private Integer id;
    private String name;
    private Double unitPrice;
}
