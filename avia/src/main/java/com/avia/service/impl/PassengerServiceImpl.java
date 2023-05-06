package com.avia.service.impl;


import com.avia.dto.requests.PassengerDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.PassengerMapper;
import com.avia.model.entity.Passenger;
import com.avia.repository.PassengerRepository;
import com.avia.service.PassengerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    @Override
    public List<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    @Override
    public Optional<Passenger> findById(Long id) {
        return passengerRepository.findById(id);
    }

    @Override
    @Transactional
    public Passenger createPassenger(PassengerDto passengerDto) {
        Passenger passenger = passengerMapper.toEntity(passengerDto);
        return passengerRepository.save(passenger);
    }


    @Override
    @Transactional
    public Passenger updatePassenger(Long id, PassengerDto passengerDto) {
        Optional<Passenger> passengerOptional = passengerRepository.findById(id);
        if (passengerOptional.isPresent()) {
            Passenger passenger = passengerMapper.partialUpdate(passengerDto, passengerOptional.get());
            return passengerRepository.save(passenger);
        } else {
            throw new EntityNotFoundException("Passenger not found with id " + id);
        }
    }
}
