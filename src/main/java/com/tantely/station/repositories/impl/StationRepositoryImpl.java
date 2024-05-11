package com.tantely.station.repositories.impl;

import com.tantely.station.entities.Station;
import com.tantely.station.repositories.StationRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StationRepositoryImpl implements StationRepository {
    private final Connection connection;

    public StationRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Station create(Station toCreate) throws SQLException {
        final var sql = "INSERT INTO stations (name, address, max_volume_gasoline, max_volume_diesel, max_volume_petrol) VALUES ( ?, ?, ?, ?, ?) ";
        try (final var stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, toCreate.getName());
            stmt.setString(2, toCreate.getAddress());
            stmt.setDouble(3, toCreate.getMaxVolumeGasoline());
            stmt.setDouble(4, toCreate.getMaxVolumeDiesel());
            stmt.setDouble(5, toCreate.getMaxVolumePetrol());
            final var rows = stmt.executeUpdate();
            if (rows > 0) {
                try (final var rs = stmt.getGeneratedKeys()) {
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
    public List<Station> findAll() throws SQLException {
        final var sql = "SELECT * FROM stations";
        final var stations = new ArrayList<Station>();
        try (final var stmt = connection.prepareStatement(sql);
             final var rs = stmt.executeQuery()){
            while (rs.next()){
                stations.add(
                        mapRsToStation(rs)
                );
            }
        }
        return stations;
    }


    @Override
    public Optional<Station> findById(Integer id) throws SQLException {
        final var sql = "SELECT * FROM stations WHERE id = ? ";
        try (final var stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);
            final var rs = stmt.executeQuery();
            if (rs.next()){
                return Optional.of(
                        mapRsToStation(rs)
                );
            }
        }
        return Optional.empty();
    }

    @Override
    public Station deleteById(Integer id) throws SQLException {
        final var savedStation = this.findById(id);
        if (savedStation.isPresent()) {
            final var sql = "DELETE FROM stations  WHERE id = ?";
            try (final var stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                final var rows = stmt.executeUpdate();
                if (rows > 0) {
                    return savedStation.get();
                }
            }
        }
        return null;
    }

    @Override
    public Station update(Station toUpdate) throws SQLException {
        final var sql = "UPDATE stations SET name = ?, address = ?, max_volume_gasoline = ?, max_volume_diesel = ?, max_volume_petrol = ? WHERE id = ?";
        try (final var stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, toUpdate.getName());
            stmt.setString(2, toUpdate.getAddress());
            stmt.setDouble(3, toUpdate.getMaxVolumeGasoline());
            stmt.setDouble(4, toUpdate.getMaxVolumeDiesel());
            stmt.setDouble(5, toUpdate.getMaxVolumePetrol());
            stmt.setInt(6, toUpdate.getId());
            final var rows = stmt.executeUpdate();
            if (rows > 0) {
                return toUpdate;
            }
            return null;
        }
    }

    private Station mapRsToStation(ResultSet rs) throws SQLException {
        return new Station()
                .setId(rs.getInt("id"))
                .setName(rs.getString("name"))
                .setAddress(rs.getString("address"))
                .setMaxVolumeGasoline(rs.getDouble("max_volume_gasoline"))
                .setMaxVolumeDiesel(rs.getDouble("max_volume_diesel"))
                .setMaxVolumePetrol(rs.getDouble("max_volume_petrol"));
    }

}
