package com.avia.controller.rest;

import com.avia.dto.PassengerDto;
import com.avia.model.entity.Passenger;
import com.avia.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/passengers")
@RequiredArgsConstructor
public class PassengerRestController {
    private final PassengerRepository passengerRepository;

    @GetMapping()
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        return new ResponseEntity<>(passengerRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Passenger> createPassenger(@RequestBody PassengerDto passengerDto) {

        Passenger build = Passenger.builder()
                .fullName(passengerDto.getFullName())
                .personalId(passengerDto.getPersonalId())
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .isDeleted(false)
                .build();

        Passenger createdPassenger = passengerRepository.save(build);

        return new ResponseEntity<>(createdPassenger, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable("id") Long id) {
        Optional<Passenger> passenger = passengerRepository.findById(id);
        return passenger.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable("id") Long id, @RequestBody PassengerDto updatedPassenger) {
        Optional<Passenger> optionalPassenger = passengerRepository.findById(id);

        if (optionalPassenger.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Passenger passenger = optionalPassenger.get();

        passenger.setFullName(updatedPassenger.getFullName());
        passenger.setPersonalId(updatedPassenger.getPersonalId());
        passenger.setChanged(Timestamp.valueOf(LocalDateTime.now()));

        Passenger updated = passengerRepository.save(passenger);

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable("id") Long id) {
        Optional<Passenger> passengerOptional = passengerRepository.findById(id);

        if (passengerOptional.isPresent()) {
            passengerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Passenger> getPassengerByFullName(@RequestParam(value = "fullName") String fullName) {
        Optional<Passenger> passenger = passengerRepository.findPassengerByFullName(fullName);
        return passenger.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search-by-pid")
    public ResponseEntity<Passenger> getPassengerByPersonalId(@RequestParam(value = "pid") String pid) {
        Optional<Passenger> passenger = passengerRepository.findPassengerByPersonalId(pid);
        return passenger.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}


