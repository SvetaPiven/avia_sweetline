package com.avia.service.impl;

import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.AirlineMapper;
import com.avia.model.entity.Airline;
import com.avia.model.request.AirlineRequest;
import com.avia.repository.AirlineRepository;
import com.avia.service.AirlineService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

    private final AirlineMapper airlineMapper;

    private final AirlineRepository airlineRepository;

    @Override
    @Transactional
    public Airline createAirline(AirlineRequest airlineRequest) {
        Airline airline = airlineMapper.toEntity(airlineRequest);
        return airlineRepository.save(airline);
    }

    @Override
    @Transactional
    public Airline updateAirline(Integer id, AirlineRequest airlineRequest) {
        Airline airline = airlineRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Airline with id " + id + " not found"));
        airlineMapper.partialUpdate(airlineRequest, airline);
        return airlineRepository.save(airline);
    }

    @Override
    public Airline findById(Integer idAirline) {
        return airlineRepository.findById(idAirline)
                .orElseThrow(() -> new EntityNotFoundException("Airline with id " + idAirline + " not found"));
    }

    @Override
    public Double calculateProfitByAirline(Long query) {
        return airlineRepository.calculateProfitByAirline(query);
    }
}
