package com.tantely.station.services;


import com.tantely.station.dtos.FuelTransactionInfo;

import java.util.List;

public interface ProductTransactionService {
    List<FuelTransactionInfo> findAllTransactionWithProductName();
}
