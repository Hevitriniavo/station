package com.tantely.station.repositories;

import com.tantely.station.dtos.SupplyStation;
import com.tantely.station.entities.Stock;

import java.sql.SQLException;
import java.util.Optional;

public interface SupplyRepository {
    Stock updateStockByIdStationAndProduct(SupplyStation toUpdate)  throws SQLException;
    Optional<Stock> findById(Integer stationId, Integer productId) throws SQLException;
}
