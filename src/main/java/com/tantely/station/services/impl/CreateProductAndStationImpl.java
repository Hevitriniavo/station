package com.tantely.station.services.impl;

import com.tantely.station.entities.Product;
import com.tantely.station.entities.Station;
import com.tantely.station.entities.Stock;
import com.tantely.station.exceptions.BadRequestException;
import com.tantely.station.exceptions.InternalServerException;
import com.tantely.station.repositories.ProductRepository;
import com.tantely.station.repositories.StationRepository;
import com.tantely.station.repositories.StockRepository;
import com.tantely.station.services.CreateProductAndStation;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class CreateProductAndStationImpl implements CreateProductAndStation {

    private final ProductRepository productRepository;
    private final StationRepository stationRepository;
    private final StockRepository stockRepository;

    public CreateProductAndStationImpl(ProductRepository productRepository, StationRepository stationRepository, StockRepository stockRepository) {
        this.productRepository = productRepository;
        this.stationRepository = stationRepository;
        this.stockRepository = stockRepository;
    }


    @Override
    public Product createProduct(Product toCreate) {
        try {
            var stations = stationRepository.findAll();
            if (stations.isEmpty()){
                throw new BadRequestException("Station is empty");
            } else {
                final var savedProduct = productRepository.create(toCreate);
                for (var station : stations){
                    var evaporationRate = getEvaporationRate(savedProduct);
                    var stock = new Stock()
                            .setStationId(station.getId())
                            .setProductId(savedProduct.getId())
                            .setQuantity(00.00)
                            .setEvaporationRate(evaporationRate);
                    stockRepository.create(stock);
                }
                return savedProduct;
            }
        } catch (SQLException e) {
            throw new InternalServerException("Error creating product");
        }
    }

    @Override
    public Station createStation(Station toCreate) {
        try {
            var products = productRepository.findAll();
            if (products.isEmpty()){
                return stationRepository.create(toCreate);
            } else {
                var station = stationRepository.create(toCreate);
                for (var product : products){
                    var evaporationRate = getEvaporationRate(product);
                    var stock = new Stock()
                            .setStationId(station.getId())
                            .setProductId(product.getId())
                            .setQuantity(00.00)
                            .setEvaporationRate(evaporationRate);
                    stockRepository.create(stock);
                }
                return station;
            }
        } catch (SQLException e) {
            throw new InternalServerException("Error creating station", e);
        }
    }

    private double getEvaporationRate(Product product) {
        return switch (product.getName().toLowerCase()) {
            case "essence" -> 100.00;
            case "pétrole" -> 10.00;
            case "gasoil" -> 50.00;
            default -> throw new InternalServerException("Unexpected value: " + product.getName().toUpperCase());
        };
    }


}
