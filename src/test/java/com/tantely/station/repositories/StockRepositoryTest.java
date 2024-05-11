package com.tantely.station.repositories;

import com.tantely.station.entities.Product;
import com.tantely.station.entities.Station;
import com.tantely.station.entities.Stock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockRepositoryTest {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private ProductRepository productRepository;


    private Stock subject;

    private Station station;

    private Product product;

    @BeforeEach
    void setUp() throws SQLException {
        var toCreateProduct = new Product()
       .setName("PRODUCT TEST")
       .setUnitPrice(10_000.00);
        var toCreateStation = new Station()
        .setName("STATION TEST")
        .setAddress("ADDRESS TEST")
        .setMaxVolumeGasoline(1500.0)
        .setMaxVolumeDiesel(2500.0)
        .setMaxVolumePetrol(3500.0);

        product = productRepository.create(toCreateProduct);
        station = stationRepository.create(toCreateStation);

        var stock = new Stock()
        .setStationId(station.getId())
        .setProductId(product.getId())
        .setQuantity(100.0)
        .setEvaporationRate(0.05);

        subject = stockRepository.create(stock);

    }


    @Test
    void findAll() throws SQLException {
        var stocks = stockRepository.findAll();
        assertNotNull(stocks);
        assertTrue(stocks.iterator().hasNext());
    }

    @Test
    void findById() throws SQLException {
        var optionalStock = stockRepository.findById(subject.getId());
        assertTrue(optionalStock.isPresent());
        var foundStock = optionalStock.get();
        assertEquals(foundStock.getId(), subject.getId());
        assertEquals(foundStock.getQuantity(), subject.getQuantity());
        assertEquals(foundStock.getEvaporationRate(), subject.getEvaporationRate());
    }

    @Test
    void update() throws SQLException {
        subject.setQuantity(200.0);
        var updatedStock = stockRepository.update(subject);
        assertNotNull(updatedStock);
        assertEquals(updatedStock.getId(), subject.getId());
        assertEquals(updatedStock.getQuantity(), 200.0);
    }


    @AfterEach
    void tearDown() throws SQLException {

        if (subject != null){
            stockRepository.deleteById(subject.getId());
        }

        if (station != null) {
            stationRepository.deleteById(station.getId());
        }

        if (product != null) {
            productRepository.deleteById(product.getId());
        }
    }
}