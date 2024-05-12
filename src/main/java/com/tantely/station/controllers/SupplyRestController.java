package com.tantely.station.controllers;

import com.tantely.station.dtos.SupplyStation;
import com.tantely.station.entities.Stock;
import com.tantely.station.services.SupplyService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class SupplyRestController {

    private final SupplyService supplyService;


    public SupplyRestController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    @PostMapping("/transactions/supply")
    public Stock updateStock(@RequestBody SupplyStation station){
        return supplyService.updateQuantity(station);
    }
}
