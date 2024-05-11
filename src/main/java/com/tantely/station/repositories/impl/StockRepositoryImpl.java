package com.tantely.station.repositories.impl;

import com.tantely.station.entities.Stock;
import com.tantely.station.repositories.StockRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StockRepositoryImpl implements StockRepository {

    private final Connection connection;

    public StockRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Stock create(Stock toCreate) throws SQLException {
        final var sql = "INSERT INTO stocks (station_id, product_id, quantity, evaporation_rate) VALUES ( ?, ?, ?, ?)";
        try (final var stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, toCreate.getStationId());
            stmt.setInt(2, toCreate.getProductId());
            stmt.setDouble(3, toCreate.getQuantity());
            stmt.setDouble(4, toCreate.getEvaporationRate());
            final var rowInserted = stmt.executeUpdate();
            if (rowInserted > 0) {
                try (var rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        var id = rs.getInt(1);
                        toCreate.setId(id);
                    }
                }
            }
            return toCreate;
        }
    }

    @Override
    public List<Stock> findAll() throws SQLException {
        final var sql = "SELECT * FROM stocks";
        final var stocks = new ArrayList<Stock>();
        try (final var stmt = connection.prepareStatement(sql);
             final var rs = stmt.executeQuery()) {
            while (rs.next()) {
                stocks.add(
                        mapRsToStock(rs)
                );
            }
        }
        return stocks;
    }


    @Override
    public Optional<Stock> findById(Integer id) throws SQLException {
        final var sql = "SELECT * FROM stocks WHERE id = ? ";
        try (final var stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            final var rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(
                        mapRsToStock(rs)
                );
            }
        }
        return Optional.empty();
    }

    @Override
    public Stock deleteById(Integer id) throws SQLException {
        final var savedStock = this.findById(id);
        if (savedStock.isPresent()) {
            final var sql = "DELETE FROM stocks  WHERE id = ?";
            try (final var stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                final var rows = stmt.executeUpdate();
                if (rows > 0) {
                    return savedStock.get();
                }
            }
        }
        return null;
    }

    @Override
    public Stock update(Stock toUpdate) throws SQLException {
        final var sql = "UPDATE stocks SET station_id = ?, product_id = ?, quantity = ?, evaporation_rate = ? WHERE id = ?";
        try (final var stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, toUpdate.getStationId());
            stmt.setInt(2, toUpdate.getProductId());
            stmt.setDouble(3, toUpdate.getQuantity());
            stmt.setDouble(4, toUpdate.getEvaporationRate());
            stmt.setInt(5, toUpdate.getId());
            final var rowUpdated = stmt.executeUpdate();
            if (rowUpdated > 0) {
                try (var rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        final var id = rs.getInt(1);
                        toUpdate.setId(id);
                    }
                }
            }
            return toUpdate;
        }
    }


    private Stock mapRsToStock(ResultSet rs) throws SQLException {
        return new Stock()
                .setId(rs.getInt("id"))
                .setStationId(rs.getInt("station_id"))
                .setProductId(rs.getInt("station_id"))
                .setQuantity(rs.getDouble("quantity"))
                .setEvaporationRate(rs.getDouble("evaporation_rate"));
    }
}
