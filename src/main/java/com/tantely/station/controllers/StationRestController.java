package com.tantely.station.controllers;

import com.tantely.station.entities.Station;
import com.tantely.station.services.StationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/stations")
public class StationRestController {

    private final StationService stationService;


    public StationRestController(StationService stationService) {
        this.stationService = stationService;
    }


    @GetMapping
    public  List<Station> getAllStations(){
        return stationService.findAllStations();
    }
}
