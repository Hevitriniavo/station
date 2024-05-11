package com.tantely.station.repositories;

import com.tantely.station.entities.Station;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface StationRepository {
   Station create(Station toCreate) throws SQLException;
   List<Station> findAll() throws SQLException;
   Optional<Station> findById(Integer id) throws SQLException;
   Station deleteById(Integer id) throws SQLException;
   Station update(Station toUpdate) throws SQLException;
}
