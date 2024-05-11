package com.tantely.station.entities;


import com.tantely.station.enums.TransactionType;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Transaction {
    private Integer id;
    private Integer stationId;
    private Integer productId;
    private TransactionType type;
    private Double quantity;
    private Double amount;
    private LocalDateTime dateTransaction;
}
