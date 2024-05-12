package com.tantely.station.services.impl;

import com.tantely.station.entities.Product;
import com.tantely.station.entities.Stock;
import com.tantely.station.repositories.ProductRepository;
import com.tantely.station.repositories.StockRepository;
import com.tantely.station.repositories.TransactionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class AutoCalculateEvaporationRate {

    private final StockRepository stockRepository;
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;

    public AutoCalculateEvaporationRate(StockRepository stockRepository, TransactionRepository transactionRepository, ProductRepository productRepository) {
        this.stockRepository = stockRepository;
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void autoCalculateEvaporationRate() throws SQLException {
        var stocks = stockRepository.findAll();
        var lastTransactionOptional = transactionRepository.findTransactionLastDate();
        if (lastTransactionOptional.isPresent()){
            var lastTransaction = lastTransactionOptional.get();
            for (Stock stock : stocks) {
                if (lastTransaction.getDateTransaction().toLocalDate().isBefore(LocalDate.now())) {
                    var product = productRepository.findById(stock.getProductId()).orElse(null);
                    if (product != null){
                        var newQuantity = getQuantity(product, stock);
                        var newStock = new Stock()
                                .setId(stock.getId())
                                .setStationId(stock.getStationId())
                                .setProductId(stock.getProductId())
                                .setQuantity(newQuantity)
                                .setEvaporationRate(stock.getEvaporationRate());
                        stockRepository.update(newStock);

                    }
                }
            }
        }

    }

    private double getQuantity(Product product, Stock stock) {
          if (product.getName().equalsIgnoreCase("essence")) {
                if (stock.getQuantity() <= stock.getEvaporationRate()){
                    return 00.00;
                } else {
                    return stock.getQuantity() - stock.getEvaporationRate();
                }
            } else if (product.getName().equalsIgnoreCase("gasoil")) {
              if (stock.getQuantity() <= stock.getEvaporationRate()){
                  return 00.00;
              } else {
                  return stock.getQuantity() - stock.getEvaporationRate();
              }
          } else if (product.getName().equalsIgnoreCase("pétrole")) {
              if (stock.getQuantity() <= stock.getEvaporationRate()){
                  return 00.00;
              } else {
                  return stock.getQuantity() - stock.getEvaporationRate();
              }
          }
          return  00.00;
    }
}
