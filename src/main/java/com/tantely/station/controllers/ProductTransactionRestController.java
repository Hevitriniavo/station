package com.tantely.station.controllers;

import com.tantely.station.dtos.FuelTransactionInfo;
import com.tantely.station.services.ProductTransactionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class ProductTransactionRestController {

    private final ProductTransactionService productTransactionService;


    public ProductTransactionRestController(ProductTransactionService productTransactionService) {
        this.productTransactionService = productTransactionService;
    }

    @GetMapping("/transaction/with/product/name")
    public List<FuelTransactionInfo> findAllTransactionWithProductName() {
        return productTransactionService.findAllTransactionWithProductName();
    }
}
