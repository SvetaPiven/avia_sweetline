package com.avia.service;

import com.avia.dto.requests.PassengerDto;
import com.avia.model.entity.Passenger;

import java.util.List;
import java.util.Optional;

public interface PassengerService {
    List<Passenger> findAll();

    Optional<Passenger> findById(Long id);

    Passenger createPassenger(PassengerDto passengerDto);

    Passenger updatePassenger(Long id, PassengerDto passengerDto);
}
