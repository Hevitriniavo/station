package com.tantely.station.repositories;

import com.tantely.station.entities.Stock;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface StockRepository {
    Stock create(Stock toCreate) throws SQLException;
    List<Stock> findAll() throws SQLException;
    Optional<Stock> findById(Integer id) throws SQLException;
    Stock deleteById(Integer id) throws SQLException;
    Stock update(Stock toUpdate) throws SQLException;
}
