package com.avia.service;

import com.avia.model.entity.Airline;
import com.avia.model.request.AirlineRequest;

public interface AirlineService {

    Airline createAirline(AirlineRequest airlineRequest);

    Airline updateAirline(Integer id, AirlineRequest airlineRequest);

    Airline findById(Integer idAirline);

    Double calculateProfitByAirline(Long query);

}
