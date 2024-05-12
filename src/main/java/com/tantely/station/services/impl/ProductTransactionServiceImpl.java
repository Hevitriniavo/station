package com.tantely.station.services.impl;


import com.tantely.station.dtos.FuelTransactionInfo;
import com.tantely.station.exceptions.InternalServerException;
import com.tantely.station.repositories.ProductTransactionRepository;
import com.tantely.station.services.ProductTransactionService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProductTransactionServiceImpl implements ProductTransactionService {

    private final ProductTransactionRepository productTransactionRepository;

    public ProductTransactionServiceImpl(ProductTransactionRepository productTransactionRepository) {
        this.productTransactionRepository = productTransactionRepository;
    }

    @Override
    public List<FuelTransactionInfo> findAllTransactionWithProductName() {
        try {
            return productTransactionRepository.findAllTransactionWithProductName();
        } catch (SQLException e) {
            throw new InternalServerException(e.getMessage());
        }
    }
}
