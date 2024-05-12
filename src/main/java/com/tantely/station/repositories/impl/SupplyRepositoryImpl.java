package com.tantely.station.repositories.impl;

import com.tantely.station.dtos.SupplyStation;
import com.tantely.station.entities.Stock;
import com.tantely.station.repositories.SupplyRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Optional;

@Repository
public class SupplyRepositoryImpl implements SupplyRepository {
    private final Connection connection;

    public SupplyRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Stock updateStockByIdStationAndProduct(SupplyStation toUpdate) throws SQLException {
        final var sql = "UPDATE stocks SET  quantity = ?  WHERE station_id = ? AND product_id = ?";
        try (final var stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, toUpdate.getQuantity());
            stmt.setInt(2, toUpdate.getStationId());
            stmt.setInt(3, toUpdate.getProductId());
            var rowUpdated = stmt.executeUpdate();
            if (rowUpdated > 0) {
                var found = this.findById(toUpdate.getStationId(), toUpdate.getProductId());
                return found.orElse(null);
            }
            return null;
        }
    }

    @Override
    public Optional<Stock> findById(Integer stationId, Integer productId) throws SQLException {
        final var sql = "SELECT * FROM stocks WHERE station_id = ? AND  product_id = ? ";
        try (final var stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, stationId);
            stmt.setInt(2, productId);
            final var rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(
                        mapRsToStock(rs)
                );
            }
        }
        return Optional.empty();
    }
    private Stock mapRsToStock(ResultSet rs) throws SQLException {
        return new Stock()
                .setId(rs.getInt("id"))
                .setStationId(rs.getInt("station_id"))
                .setProductId(rs.getInt("product_id"))
                .setQuantity(rs.getDouble("quantity"))
                .setEvaporationRate(rs.getDouble("evaporation_rate"));
    }
}
