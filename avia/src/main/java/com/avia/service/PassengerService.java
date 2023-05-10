package com.avia.service;

import com.avia.model.entity.requests.PassengerDto;
import com.avia.model.entity.Passenger;

public interface PassengerService {

    Passenger createPassenger(PassengerDto passengerDto);

    Passenger updatePassenger(Long id, PassengerDto passengerDto);
}
