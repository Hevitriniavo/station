package com.tantely.station.repositories;

import com.tantely.station.entities.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product subject;

    @BeforeEach
    void setUp() throws SQLException {
        var toCreate = new Product();
        toCreate.setName("PRODUCT TEST");
        toCreate.setUnitPrice(10_000.00);
        subject = productRepository.create(toCreate);
    }

    @Test
    void findAll() throws SQLException {
        var products = productRepository.findAll();
        assertTrue(products.iterator().hasNext());
    }

    @Test
    void update() throws SQLException {
        subject.setName("UPDATED NAME");
        subject.setUnitPrice(15_000.00);
        var updatedProduct = productRepository.update(subject);
        assertNotNull(updatedProduct);
        assertEquals(updatedProduct.getId(), subject.getId());
        assertEquals(updatedProduct.getName(), "UPDATED NAME");
        assertEquals(updatedProduct.getUnitPrice(), 15_000.00);
    }

    @Test
    void findById() throws SQLException {
        var optionalProduct = productRepository.findById(subject.getId());
        assertTrue(optionalProduct.isPresent());
        var foundProduct = optionalProduct.get();
        assertEquals(foundProduct.getId(), subject.getId());
        assertEquals(foundProduct.getName(), subject.getName());
        assertEquals(foundProduct.getUnitPrice(), subject.getUnitPrice());
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (subject != null) {
            productRepository.deleteById(subject.getId());
        }
    }
}
