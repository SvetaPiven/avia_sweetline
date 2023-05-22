package com.avia.service;

import com.avia.model.entity.Airport;
import com.avia.model.request.AirportRequest;

public interface AirportService {

    Airport createAirport(AirportRequest airportRequest);

    Airport updateAirport(Long id, AirportRequest airportRequest);

    String getAddressFromLatLng(Float latitude, Float longitude) throws Exception;

    Airport findById(Long idFlight);

}
