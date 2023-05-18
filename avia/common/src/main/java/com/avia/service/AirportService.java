package com.avia.service;

import com.avia.model.dto.AirportDto;
import com.avia.model.entity.Airport;

public interface AirportService {

    Airport createAirport(AirportDto airportDto);

    Airport updateAirport(Long id, AirportDto airportDto);

    String getAddressFromLatLng(Float latitude, Float longitude) throws Exception;

}