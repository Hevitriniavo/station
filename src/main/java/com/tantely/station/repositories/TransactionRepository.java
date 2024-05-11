package com.tantely.station.repositories;

import com.tantely.station.entities.Transaction;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository {
    Transaction create(Transaction toCreate) throws SQLException;
    List<Transaction> findAll() throws SQLException;
    Optional<Transaction> findById(Integer id) throws SQLException;
    Transaction deleteById(Integer id) throws SQLException;
    Transaction update(Transaction toUpdate) throws SQLException;
}
