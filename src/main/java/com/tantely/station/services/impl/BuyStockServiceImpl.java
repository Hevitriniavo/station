package com.tantely.station.services.impl;

import com.tantely.station.dtos.BuyStockByMoney;
import com.tantely.station.dtos.BuyStockByQuantity;
import com.tantely.station.dtos.SupplyStation;
import com.tantely.station.entities.Transaction;
import com.tantely.station.enums.TransactionType;
import com.tantely.station.exceptions.BadRequestException;
import com.tantely.station.exceptions.InternalServerException;
import com.tantely.station.repositories.ProductRepository;
import com.tantely.station.repositories.SupplyRepository;
import com.tantely.station.repositories.TransactionRepository;
import com.tantely.station.services.BuyStockService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;

@Service
public class BuyStockServiceImpl implements BuyStockService {
    private final SupplyRepository supplyRepository;
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;

    public BuyStockServiceImpl(SupplyRepository supplyRepository, TransactionRepository transactionRepository, ProductRepository productRepository) {
        this.supplyRepository = supplyRepository;
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Double buyStockByMoney(BuyStockByMoney buyStockByMoney) {
        try {
            return buyStock(buyStockByMoney.getStationId(), buyStockByMoney.getProductId(), buyStockByMoney.getAmount() / getProductUnitPrice(buyStockByMoney.getProductId()), buyStockByMoney.getAmount());
        } catch (SQLException e) {
            throw new InternalServerException(e.getMessage());
        }
    }

    @Override
    public Double buyStockByQuantity(BuyStockByQuantity buyStockByQuantity) {
        try {
            return buyStock(buyStockByQuantity.getStationId(), buyStockByQuantity.getProductId(), buyStockByQuantity.getQuantity(), buyStockByQuantity.getQuantity() * getProductUnitPrice(buyStockByQuantity.getProductId()));
        } catch (SQLException e) {
            throw new InternalServerException(e.getMessage());
        }
    }

    private Double buyStock(int stationId, int productId, double quantity, double amount) {
        try {
            var oldStock = supplyRepository.findById(stationId, productId);
            var product = productRepository.findById(productId);
            if (product.isEmpty()) {
                throw new BadRequestException("Product not found");
            }
            if (oldStock.isEmpty()) {
                throw new BadRequestException("Station not found");
            }

            var newQuantity = oldStock.get().getQuantity() - quantity;

            if (newQuantity <= 0) {
                throw new BadRequestException("Quantity insufficient");
            }
            var toUpdate = new SupplyStation();
            toUpdate.setQuantity(newQuantity);
            toUpdate.setProductId(productId);
            toUpdate.setStationId(stationId);
            var stocked = supplyRepository.updateStockByIdStationAndProduct(toUpdate);
            if (stocked != null) {
                var transaction = new Transaction()
                        .setAmount(amount)
                        .setStationId(stationId)
                        .setProductId(productId)
                        .setType(TransactionType.SORTIE)
                        .setQuantity(quantity)
                        .setDateTransaction(LocalDateTime.now());
                transactionRepository.create(transaction);
            }
            return newQuantity;
        } catch (SQLException e) {
            throw new InternalServerException(e.getMessage());
        }
    }

    private double getProductUnitPrice(int productId) throws SQLException {
        var product = productRepository.findById(productId);
        if (product.isPresent()) {
            return product.get().getUnitPrice();
        } else {
            throw new BadRequestException("Product not found");
        }
    }
}
