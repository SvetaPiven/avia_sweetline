package com.avia.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class TicketPriceCalculator {

    private final CalculateDistance calculateDistance;

    public BigDecimal calculateTicketPrice(double latitudeDeparture, double longitudeDeparture,
                                           double latitudeArrival, double longitudeArrival) {
        double distance = calculateDistance.calculate(latitudeDeparture, longitudeDeparture, latitudeArrival, longitudeArrival);
        double price = distance * 0.3;
        return BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP);
    }
}