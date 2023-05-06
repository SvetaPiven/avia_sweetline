package com.avia.service.impl;

import com.avia.dto.requests.FlightStatusDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.FlightStatusMapper;
import com.avia.model.entity.DocumentType;
import com.avia.model.entity.FlightStatus;
import com.avia.repository.FlightStatusRepository;
import com.avia.service.FlightStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightStatusServiceImpl implements FlightStatusService {

    private final FlightStatusMapper flightStatusMapper;
    private final FlightStatusRepository flightStatusRepository;

    @Override
    public FlightStatus createFlightStatus(FlightStatusDto flightStatusDto) {

        FlightStatus flightStatus = flightStatusMapper.toEntity(flightStatusDto);

        return flightStatusRepository.save(flightStatus);
    }

    @Override
    public FlightStatus updateFlightStatus(Integer id, FlightStatusDto flightStatusDto) {

        FlightStatus flightStatus = flightStatusRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Flight status with id " + id + " not found"));

        flightStatusMapper.partialUpdate(flightStatusDto, flightStatus);

        return flightStatusRepository.save(flightStatus);
    }
}
