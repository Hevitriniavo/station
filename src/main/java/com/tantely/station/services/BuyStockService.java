package com.tantely.station.services;

import com.tantely.station.dtos.BuyStockByMoney;
import com.tantely.station.dtos.BuyStockByQuantity;

public interface BuyStockService {
    Double buyStockByMoney(BuyStockByMoney buyStockByMoney);
    Double buyStockByQuantity(BuyStockByQuantity buyStockByQuantity);
}
