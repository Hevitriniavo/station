package com.tantely.station.repositories;

import com.tantely.station.entities.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
   Product create(Product toCreate) throws SQLException;
   List<Product> findAll() throws SQLException;
   Optional<Product> findById(Integer id) throws SQLException;
   Product deleteById(Integer id) throws SQLException;
   Product update(Product toUpdate) throws SQLException;
   Optional<Product> findByName(String  name) throws SQLException;
}
