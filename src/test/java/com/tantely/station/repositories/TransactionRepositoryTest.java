package com.tantely.station.repositories;

import com.tantely.station.entities.Product;
import com.tantely.station.entities.Station;
import com.tantely.station.entities.Transaction;
import com.tantely.station.enums.TransactionType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private ProductRepository productRepository;

    private Transaction subject;
    private Station station;
    private Product product;

    @BeforeEach
    void setUp() throws SQLException {
        var toCreateProduct = new Product()
                .setName("PRODUCT TEST")
                .setUnitPrice(10_000.00);
        product = productRepository.create(toCreateProduct);

        var toCreateStation = new Station()
                .setName("STATION TEST")
                .setAddress("ADDRESS TEST")
                .setMaxVolumeGasoline(1500.0)
                .setMaxVolumeDiesel(2500.0)
                .setMaxVolumePetrol(3500.0);
        station = stationRepository.create(toCreateStation);

        var toCreateTransaction = new Transaction()
                .setStationId(station.getId())
                .setProductId(product.getId())
                .setType(TransactionType.ENTRY)
                .setDateTransaction(LocalDateTime.now())
                .setQuantity(100.0)
                .setAmount(10_000.00);
        subject = transactionRepository.create(toCreateTransaction);
    }

    @Test
    void findAll() throws SQLException {
        var transactions = transactionRepository.findAll();
        assertNotNull(transactions);
        assertTrue(transactions.iterator().hasNext());
    }

    @Test
    void findById() throws SQLException {
        var optionalTransaction = transactionRepository.findById(subject.getId());
        assertTrue(optionalTransaction.isPresent());
        var foundTransaction = optionalTransaction.get();
        assertEquals(foundTransaction.getId(), subject.getId());
    }

    @Test
    void update() throws SQLException {
        subject.setQuantity(200.0);
        var updatedTransaction = transactionRepository.update(subject);
        assertNotNull(updatedTransaction);
        assertEquals(updatedTransaction.getId(), subject.getId());
        assertEquals(updatedTransaction.getQuantity(), 200.0);
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (subject != null) {
            transactionRepository.deleteById(subject.getId());
        }
        if (station != null) {
            stationRepository.deleteById(station.getId());
        }
        if (product != null) {
            productRepository.deleteById(product.getId());
        }
    }
}
