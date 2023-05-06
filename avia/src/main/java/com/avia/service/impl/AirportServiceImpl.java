package com.avia.service.impl;

import com.avia.dto.requests.AirportDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.AirportMapper;
import com.avia.model.entity.Airport;
import com.avia.repository.AirportRepository;
import com.avia.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService{

        private final AirportMapper airportMapper;
        private final AirportRepository airportRepository;

        @Override
        public Airport createAirport(AirportDto airportDto) {

            Airport airport = airportMapper.toEntity(airportDto);

            return airportRepository.save(airport);
        }

        @Override
        public Airport updateAirport(Long id, AirportDto airportDto) {

            Airport airport = airportRepository.findById(id).orElseThrow(() ->
                    new EntityNotFoundException("Airport with id " + id + " not found"));

            airportMapper.partialUpdate(airportDto, airport);

            return airportRepository.save(airport);
        }
}
