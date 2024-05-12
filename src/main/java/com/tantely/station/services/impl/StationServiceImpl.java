package com.tantely.station.services.impl;


import com.tantely.station.entities.Station;
import com.tantely.station.exceptions.InternalServerException;
import com.tantely.station.repositories.StationRepository;
import com.tantely.station.services.StationService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;

    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }


    @Override
    public List<Station> findAllStations() {
        try {
            return stationRepository.findAll();
        } catch (SQLException e) {
            throw new InternalServerException("Error reading stations");
        }
    }

}
