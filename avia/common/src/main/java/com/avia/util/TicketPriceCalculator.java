package com.avia.util;

import com.avia.model.entity.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class TicketPriceCalculator {

    private final CalculateDistance calculateDistance;

    private final FlightCoordinatesTaker flightCoordinatesTaker;

    public BigDecimal calculateTicketPrice(Flight flight) {
        double distance = calculateDistance.calculate(flightCoordinatesTaker.flightCoordinatesTaker(flight));
        double price = distance * 0.3;
        return BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP);
    }
}