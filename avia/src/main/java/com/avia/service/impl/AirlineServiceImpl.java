package com.avia.service.impl;

import com.avia.dto.requests.AirlineDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.AirlineMapper;
import com.avia.model.entity.Airline;
import com.avia.model.entity.Passenger;
import com.avia.model.entity.Ticket;
import com.avia.repository.AirlineRepository;
import com.avia.repository.AirportRepository;
import com.avia.service.AirlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

    private final AirlineMapper airlineMapper;
    private final AirlineRepository airlineRepository;

    @Override
    public Airline createAirline(AirlineDto airlineDto) {
        Airline airline = airlineMapper.toEntity(airlineDto);
        return airlineRepository.save(airline);
    }

    @Override
    public Airline updateAirline(Integer id, AirlineDto airlineDto) {
        Airline airline = airlineRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Airline with id " + id + " not found"));
        airlineMapper.partialUpdate(airlineDto, airline);
        return airlineRepository.save(airline);
    }
}
