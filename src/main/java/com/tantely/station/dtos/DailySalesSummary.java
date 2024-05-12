package com.tantely.station.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class DailySalesSummary {
        private LocalDate date;
        private Double fuelAddedEssence;
        private Double fuelAddedGasoline;
        private Double fuelAddedDiesel;
        private Double fuelSoldEssence;
        private Double fuelSoldGasoline;
        private Double fuelSoldDiesel;
        private Double remainingQuantityEssence;
        private Double remainingQuantityGasoline;
        private Double remainingQuantityDiesel;
}
