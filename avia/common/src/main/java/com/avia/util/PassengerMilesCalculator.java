package com.avia.util;

import com.avia.model.entity.Passenger;
import com.avia.repository.PassengerRepository;
import com.avia.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PassengerMilesCalculator {

    private final LocationService locationService;

    private final PassengerRepository passengerRepository;

    private boolean scheduledExecutionFlag = false;

    private static final Logger log = Logger.getLogger(PassengerMilesCalculator.class);

    @Scheduled(cron = "0 0 0 * * *")
    public void calculatePassengerMilesUtil() {

        scheduledExecutionFlag = true;
        int counterCalculations = 0;

        List<Passenger> passengers = passengerRepository.findAll();
        for (Passenger passenger : passengers) {
            locationService.calculatePassengerMiles(passenger);
            counterCalculations ++;
        }
        log.error("Scheduled method calculate miles for " + counterCalculations + " passengers.");
    }

    @Scheduled(cron = "0 1 0 * * *")
    public void checkScheduledExecution() {
        if (!scheduledExecutionFlag) {
            log.error("Scheduled method did not execute at the expected time.");
        }
        scheduledExecutionFlag = false;
    }
}