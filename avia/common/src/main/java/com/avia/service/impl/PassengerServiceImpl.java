package com.avia.service.impl;


import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.PassengerMapper;
import com.avia.model.entity.Passenger;
import com.avia.model.request.PassengerRequest;
import com.avia.repository.PassengerRepository;
import com.avia.service.PassengerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;

    private final PassengerMapper passengerMapper;

    @Override
    @Transactional
    public Passenger createPassenger(PassengerRequest passengerRequest) {
        Passenger passenger = passengerMapper.toEntity(passengerRequest);
        return passengerRepository.save(passenger);
    }


    @Override
    @Transactional
    public Passenger updatePassenger(Long id, PassengerRequest passengerRequest) {
        Optional<Passenger> passengerOptional = passengerRepository.findById(id);
        if (passengerOptional.isPresent()) {
            Passenger passenger = passengerMapper.partialUpdate(passengerRequest, passengerOptional.get());
            return passengerRepository.save(passenger);
        } else {
            throw new EntityNotFoundException("Passenger not found with id " + id);
        }
    }

    @Override
    public Passenger findById(Long idPass) {
        return passengerRepository.findById(idPass)
                .orElseThrow(() -> new EntityNotFoundException("Passenger with id " + idPass + " not found"));
    }
}
