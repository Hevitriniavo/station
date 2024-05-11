package com.tantely.station.repositories.impl;

import com.tantely.station.entities.Product;
import com.tantely.station.repositories.ProductRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final Connection connection;

    public ProductRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Product create(Product toCreate) throws SQLException {
        final var sql = "INSERT INTO products (name, unit_price) VALUES (?, ?)";
        try (final var stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, toCreate.getName());
            stmt.setDouble(2, toCreate.getUnitPrice());
            final var rows = stmt.executeUpdate();
            if (rows > 0) {
                try (final var rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        final var id = rs.getInt(1);
                        toCreate.setId(id);
                    }
                }
            }
            return toCreate;
        }
    }

    @Override
    public List<Product> findAll() throws SQLException {
        final var sql = "SELECT * FROM products";
        final var products = new ArrayList<Product>();
        try (final var stmt = connection.prepareStatement(sql);
             final var rs = stmt.executeQuery()){
              while (rs.next()){
                products.add(
                        mapRsToProduct(rs)
                );
            }
        }
        return products;
    }


    @Override
    public Optional<Product> findById(Integer id) throws SQLException {
        final var sql = "SELECT * FROM products WHERE id = ? ";
        try (final var stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);
            final var rs = stmt.executeQuery();
            if (rs.next()){
                return Optional.of(
                    mapRsToProduct(rs)
                );
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Product> findByName(String name) throws SQLException {
        final var sql = "SELECT * FROM products WHERE name = ? ";
        try (final var stmt = connection.prepareStatement(sql)){
            stmt.setString(1, name);
            final var rs = stmt.executeQuery();
            if (rs.next()){
                return Optional.of(
                        mapRsToProduct(rs)
                );
            }
        }
        return Optional.empty();
    }

    @Override
    public Product deleteById(Integer id) throws SQLException {
        final var savedProduct = this.findById(id);
        if (savedProduct.isPresent()) {
            final var sql = "DELETE FROM products  WHERE id = ?";
            try (final var stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                final var rows = stmt.executeUpdate();
                if (rows > 0) {
                    return savedProduct.get();
                }
            }
        }
        return null;
    }

    @Override
    public Product update(Product toUpdate) throws SQLException {
        final var sql = "UPDATE products  SET name = ?, unit_price = ? WHERE id = ?";
        try (final var stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, toUpdate.getName());
            stmt.setDouble(2, toUpdate.getUnitPrice());
            stmt.setInt(3, toUpdate.getId());
            final var rows = stmt.executeUpdate();
            if (rows > 0) {
                return toUpdate;
            }
            return null;
        }
    }


    private Product mapRsToProduct(ResultSet rs) throws SQLException {
        return new Product()
                .setId(rs.getInt("id"))
                .setName(rs.getString("name"))
                .setUnitPrice(rs.getDouble("unit_price"));
    }

}
