package com.avia.util;

import com.avia.model.entity.Passenger;
import com.avia.repository.PassengerRepository;
import com.avia.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PassengerMilesCalculator {

    private final LocationService locationService;
    private final PassengerRepository passengerRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void calculatePassengerMiles() {
        List<Passenger> passengers = passengerRepository.findAll();
        for (Passenger passenger : passengers) {
            locationService.calculatePassengerMiles(passenger);
        }
    }
}