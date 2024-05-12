package com.tantely.station.repositories;


import com.tantely.station.dtos.FuelTransactionInfo;

import java.sql.SQLException;
import java.util.List;

public interface ProductTransactionRepository {

    List<FuelTransactionInfo> findAllTransactionWithProductName() throws SQLException;
}
