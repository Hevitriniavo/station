package com.tantely.station.controllers;

import com.tantely.station.entities.Product;
import com.tantely.station.entities.Station;
import com.tantely.station.services.CreateProductAndStation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class CreateStationAndProductRestController {

    private final CreateProductAndStation createProductAndStation;

    public CreateStationAndProductRestController(CreateProductAndStation createProductAndStation) {
        this.createProductAndStation = createProductAndStation;
    }

    @PostMapping("/products/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product toCreate){
        return createProductAndStation.createProduct(toCreate);
    }

    @PostMapping("/stations/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Station createStation(@RequestBody Station toCreate){
        return createProductAndStation.createStation(toCreate);
    }
}
