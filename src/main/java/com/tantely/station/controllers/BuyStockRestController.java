package com.tantely.station.controllers;

import com.tantely.station.dtos.BuyStockByMoney;
import com.tantely.station.dtos.BuyStockByQuantity;
import com.tantely.station.services.BuyStockService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class BuyStockRestController {
    private final BuyStockService buyStockService;

    public BuyStockRestController(BuyStockService buyStockService) {
        this.buyStockService = buyStockService;
    }


    @PostMapping("/buy/stock/quantity")
    public Double buyStockByQuantity(@RequestBody BuyStockByQuantity quantity) {
        return buyStockService.buyStockByQuantity(quantity);
    }

    @PostMapping("/buy/stock/money")
    private Double buyStockByMoney(@RequestBody BuyStockByMoney buyStockByMoney) {
        return buyStockService.buyStockByMoney(buyStockByMoney);
    }
}
