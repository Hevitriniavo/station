package com.tantely.station.services;

import com.tantely.station.entities.Product;
import com.tantely.station.entities.Station;

public interface CreateProductAndStation {
    Product createProduct(Product toCreate);
    Station createStation(Station toCreate);
}
