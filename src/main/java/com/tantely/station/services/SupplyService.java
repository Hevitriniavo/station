package com.tantely.station.services;

import com.tantely.station.dtos.SupplyStation;
import com.tantely.station.entities.Stock;

public interface SupplyService {
    Stock updateQuantity(SupplyStation toUpdate);
}
