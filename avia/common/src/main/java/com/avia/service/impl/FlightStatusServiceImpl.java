package com.avia.service.impl;

import com.avia.model.entity.FlightStatus;
import com.avia.repository.FlightStatusRepository;
import com.avia.service.FlightStatusService;
import com.avia.model.dto.FlightStatusDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.FlightStatusMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightStatusServiceImpl implements FlightStatusService {

    private final FlightStatusMapper flightStatusMapper;
    private final FlightStatusRepository flightStatusRepository;

    @Override
    @Transactional
    public FlightStatus createFlightStatus(FlightStatusDto flightStatusDto) {

        FlightStatus flightStatus = flightStatusMapper.toEntity(flightStatusDto);

        return flightStatusRepository.save(flightStatus);
    }

    @Override
    @Transactional
    public FlightStatus updateFlightStatus(Integer id, FlightStatusDto flightStatusDto) {

        FlightStatus flightStatus = flightStatusRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Flight status with id " + id + " not found"));

        flightStatusMapper.partialUpdate(flightStatusDto, flightStatus);

        return flightStatusRepository.save(flightStatus);
    }
}