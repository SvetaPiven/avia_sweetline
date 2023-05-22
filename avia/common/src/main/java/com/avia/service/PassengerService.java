package com.avia.service;

import com.avia.model.entity.Passenger;
import com.avia.model.request.PassengerRequest;

public interface PassengerService {

    Passenger createPassenger(PassengerRequest passengerRequest);

    Passenger updatePassenger(Long id, PassengerRequest passengerRequest);

    Passenger findById(Long idPass);
}
