package com.avia.util;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CalculateDistance {

    public double calculate(List<Double> coordinates) {
        double lat1 = coordinates.get(0);
        double lon1 = coordinates.get(1);
        double lat2 = coordinates.get(2);
        double lon2 = coordinates.get(3);

        double earthRadius = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;
        return distance * 0.621371;
    }
}
