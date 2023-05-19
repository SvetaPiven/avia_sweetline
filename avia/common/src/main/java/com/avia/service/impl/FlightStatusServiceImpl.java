package com.avia.service.impl;

import com.avia.model.entity.FlightStatus;
import com.avia.repository.FlightStatusRepository;
import com.avia.service.FlightStatusService;
import com.avia.model.request.FlightStatusRequest;
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
    public FlightStatus createFlightStatus(FlightStatusRequest flightStatusRequest) {

        FlightStatus flightStatus = flightStatusMapper.toEntity(flightStatusRequest);

        return flightStatusRepository.save(flightStatus);
    }

    @Override
    @Transactional
    public FlightStatus updateFlightStatus(Integer id, FlightStatusRequest flightStatusRequest) {

        FlightStatus flightStatus = flightStatusRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Flight status with id " + id + " not found"));

        flightStatusMapper.partialUpdate(flightStatusRequest, flightStatus);

        return flightStatusRepository.save(flightStatus);
    }
}
