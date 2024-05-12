package com.tantely.station.services.impl;

import com.tantely.station.dtos.BuyStockByMoney;
import com.tantely.station.dtos.BuyStockByQuantity;
import com.tantely.station.dtos.SupplyStation;
import com.tantely.station.entities.Transaction;
import com.tantely.station.enums.TransactionType;
import com.tantely.station.exceptions.BadRequestException;
import com.tantely.station.exceptions.InternalServerException;
import com.tantely.station.exceptions.NotFoundException;
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
            verifyStationAndProduct(buyStockByMoney.getStationId(), buyStockByMoney.getProductId());
            var requestQuantity = buyStockByMoney.getAmount() / getProductUnitPrice(buyStockByMoney.getProductId());
            return buyStock(buyStockByMoney.getStationId(), buyStockByMoney.getProductId(), requestQuantity, buyStockByMoney.getAmount());
        } catch (SQLException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public Double buyStockByQuantity(BuyStockByQuantity buyStockByQuantity) {
        try {
            verifyStationAndProduct(buyStockByQuantity.getStationId(), buyStockByQuantity.getProductId());
            double totalPrice = buyStockByQuantity.getQuantity() * getProductUnitPrice(buyStockByQuantity.getProductId());
            buyStock(buyStockByQuantity.getStationId(), buyStockByQuantity.getProductId(), buyStockByQuantity.getQuantity(), totalPrice);
            return totalPrice;
        } catch (SQLException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private double getProductUnitPrice(int productId) throws SQLException {
        var product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new BadRequestException("Product not found");
        }
        return product.get().getUnitPrice();
    }

    private Double buyStock(int stationId, int productId, double quantity, double amount) throws SQLException {
        var oldStock = supplyRepository.findById(stationId, productId);
        if (oldStock.isEmpty()) {
            throw new NotFoundException("Stock not found");
        }
        double newQuantity = oldStock.get().getQuantity() - quantity;

        if (newQuantity < 0) {
            throw new BadRequestException("Quantity insufficient");
        }

        var toUpdate = new SupplyStation();
        toUpdate.setQuantity(newQuantity);
        toUpdate.setProductId(productId);
        toUpdate.setStationId(stationId);
        var stocked = supplyRepository.updateStockByIdStationAndProduct(toUpdate);
        if (stocked != null) {
            createTransaction(amount, stationId, productId, quantity);
        }
        return quantity;
    }

    private void createTransaction(double amount, int stationId, int productId, double quantity) {
        var transaction = new Transaction()
                .setAmount(amount)
                .setStationId(stationId)
                .setProductId(productId)
                .setType(TransactionType.SORTIE)
                .setQuantity(quantity)
                .setDateTransaction(LocalDateTime.now());
        try {
            transactionRepository.create(transaction);
        } catch (SQLException e) {
            throw new InternalServerException(e.getMessage());
        }
    }

    private void verifyStationAndProduct(int stationId, int productId) throws SQLException {
        var oldStock = supplyRepository.findById(stationId, productId);
        if (oldStock.isEmpty()) {
            throw new BadRequestException("Station not found");
        }
    }
}
