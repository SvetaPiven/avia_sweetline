package com.avia.service.impl;


import com.avia.dto.PassengerCreateDto;
import com.avia.dto.PassengerUpdateDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.PassengerCreateMapper;
import com.avia.mapper.PassengerUpdateMapper;
import com.avia.model.entity.Passenger;
import com.avia.repository.PassengerRepository;
import com.avia.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerCreateMapper passengerCreateMapper;
    private final PassengerUpdateMapper passengerUpdateMapper;

    @Override
    public List<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    @Override
    public Optional<Passenger> findById(Long id) {
        return passengerRepository.findById(id);
    }

    @Override
    public Passenger createPassenger(PassengerCreateDto passengerCreateDto) {
        Passenger passenger = passengerCreateMapper.toEntity(passengerCreateDto);
        return passengerRepository.save(passenger);
    }

    @Override
    public Passenger updatePassenger(Long id, PassengerUpdateDto passengerUpdateDto) {
        Optional<Passenger> passengerOptional = passengerRepository.findById(id);
        if (passengerOptional.isPresent()) {
            Passenger passenger = passengerUpdateMapper.partialUpdate(passengerUpdateDto, passengerOptional.get());;
            passenger.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            return passengerRepository.save(passenger);
        } else {
            throw new EntityNotFoundException("Passenger not found with id " + id);
        }
    }
}
