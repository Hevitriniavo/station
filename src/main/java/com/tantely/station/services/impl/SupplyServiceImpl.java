package com.tantely.station.services.impl;

import com.tantely.station.dtos.SupplyStation;
import com.tantely.station.entities.Stock;
import com.tantely.station.entities.Transaction;
import com.tantely.station.enums.TransactionType;
import com.tantely.station.exceptions.BadRequestException;
import com.tantely.station.exceptions.InternalServerException;
import com.tantely.station.repositories.SupplyRepository;
import com.tantely.station.repositories.TransactionRepository;
import com.tantely.station.services.SupplyService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;


@Service
public class SupplyServiceImpl implements SupplyService {
    private final SupplyRepository supplyRepository;
    private final TransactionRepository transactionRepository;

    public SupplyServiceImpl(SupplyRepository supplyRepository, TransactionRepository transactionRepository) {
        this.supplyRepository = supplyRepository;
        this.transactionRepository = transactionRepository;
    }


    @Override
    public Stock updateQuantity(SupplyStation toUpdate) {
        try {
            var oldStock = supplyRepository.findById(toUpdate.getStationId(), toUpdate.getProductId());
            if (oldStock.isEmpty()) {
                throw new BadRequestException("Stock not found");
            }
            var requestQuantity = toUpdate.getQuantity();
            var newQuantity = oldStock.get().getQuantity() + requestQuantity;

            toUpdate.setQuantity(newQuantity);
            var stocked = supplyRepository.updateStockByIdStationAndProduct(toUpdate);
            var transaction = new Transaction()
                    .setAmount(00.00)
                    .setStationId(toUpdate.getStationId())
                    .setProductId(toUpdate.getProductId())
                    .setType(TransactionType.ENTRY)
                    .setQuantity(requestQuantity)
                    .setDateTransaction(LocalDateTime.now());
            transactionRepository.create(transaction);
            return stocked;
        } catch (SQLException e) {
            throw new InternalServerException(e.getMessage());
        }
    }
}
