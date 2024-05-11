package com.tantely.station.repositories;

import com.tantely.station.entities.Station;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    private Station subject;

    @BeforeEach
    void setUp() throws SQLException {
        var toCreate = new Station()
        .setName("STATION TEST")
        .setAddress("ADDRESS TEST")
        .setMaxVolumeGasoline(1500.0)
        .setMaxVolumeDiesel(2500.0)
        .setMaxVolumePetrol(3500.0);
        subject = stationRepository.create(toCreate);
    }

    @Test
    void findAll() throws SQLException {
        var stations = stationRepository.findAll();
        assertTrue(stations.iterator().hasNext());
    }

    @Test
    void update() throws SQLException {
        subject.setName("UPDATED NAME");
        subject.setAddress("ADDRESS UPDATED");
        subject.setMaxVolumeGasoline(1400.0);
        subject.setMaxVolumeDiesel(2400.0);
        subject.setMaxVolumePetrol(3400.0);
        var updatedStation = stationRepository.update(subject);
        assertNotNull(updatedStation);
        assertEquals(updatedStation, subject);
    }

    @Test
    void findById() throws SQLException {
        var optionalStation = stationRepository.findById(subject.getId());
        assertTrue(optionalStation.isPresent());
        assertEquals(optionalStation.get(), subject);
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (subject != null) {
            stationRepository.deleteById(subject.getId());
        }
    }
}
