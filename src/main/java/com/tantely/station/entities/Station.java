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
public class Station implements Serializable {
    private Integer id;
    private String name;
    private String address;
    private Double maxVolumeGasoline;
    private Double maxVolumeDiesel;
    private Double maxVolumePetrol;
}
