package com.avia.service;

import com.avia.dto.PassengerCreateDto;
import com.avia.dto.PassengerUpdateDto;
import com.avia.model.entity.Passenger;

import java.util.List;
import java.util.Optional;

public interface PassengerService {
    List<Passenger> findAll();

    Optional<Passenger> findById(Long id);

    Passenger createPassenger(PassengerCreateDto passengerCreateDto);

    Passenger updatePassenger(Long id, PassengerUpdateDto passengerUpdateDto);
}
