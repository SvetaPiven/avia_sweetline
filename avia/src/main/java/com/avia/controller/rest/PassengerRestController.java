package com.avia.controller.rest;

import com.avia.dto.PassengerCreateDto;
import com.avia.dto.PassengerUpdateDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.model.entity.Passenger;
import com.avia.repository.PassengerRepository;
import com.avia.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/passengers")
@RequiredArgsConstructor
public class
PassengerRestController {

    private final PassengerRepository passengerRepository;

    private final PassengerService passengerService;

    private static final Logger log = Logger.getLogger(PassengerRestController.class);

    @GetMapping()
    public ResponseEntity<List<Passenger>> getAllPassengers() {

        return new ResponseEntity<>(passengerRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Passenger> createPassenger(@RequestBody PassengerCreateDto passengerCreateDto) {

        Passenger createdPassenger = passengerService.createPassenger(passengerCreateDto);

        return new ResponseEntity<>(createdPassenger, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable("id") Long id) {

        Optional<Passenger> passenger = passengerRepository.findById(id);

        return passenger.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("Passenger with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id, @RequestBody PassengerUpdateDto passengerUpdateDto) {

        Passenger updatedPassenger = passengerService.updatePassenger(id, passengerUpdateDto);

        return new ResponseEntity<>(updatedPassenger, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable("id") Long id) {

        Optional<Passenger> passengerOptional = passengerRepository.findById(id);

        if (passengerOptional.isPresent()) {
            passengerRepository.deleteById(id);
            log.info("Passenger with this id is deleted!");
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Passenger with" + id + "not found!");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Passenger> getPassengerByFullName(@RequestParam(value = "fullName") String fullName) {

        Optional<Passenger> passenger = passengerRepository.findPassengerByFullName(fullName);

        return passenger.map(ResponseEntity::ok).orElseThrow(() -> new EntityNotFoundException("Passenger with fullName " + fullName + " not found"));
    }

    @GetMapping("/search-by-personal-id")
    public ResponseEntity<Passenger> getPassengerByPersonalId(@RequestParam(value = "pid") String personalId) {

        Optional<Passenger> passenger = passengerRepository.findPassengerByPersonalId(personalId);

        return passenger.map(ResponseEntity::ok).orElseThrow(() -> new EntityNotFoundException("Passenger with personalId " + personalId + " not found"));
    }

//    @PostMapping
//    public ResponseEntity<Passenger> createPassenger(@RequestBody PassengerDto passengerDto) {
//
//        Passenger build = Passenger.builder()
//                .fullName(passengerDto.getFullName())
//                .personalId(passengerDto.getPersonalId())
//                .created(Timestamp.valueOf(LocalDateTime.now()))
//                //.isDeleted(false)
//                .build();
//
//        Passenger createdPassenger = passengerRepository.save(build);
//
//        return new ResponseEntity<>(createdPassenger, HttpStatus.CREATED);
//    }
    //    @PutMapping("/{id}")
//    public ResponseEntity<Passenger> updatePassenger(@PathVariable("id") Long id, @RequestBody PassengerDto updatedPassenger) {
//        Optional<Passenger> optionalPassenger = passengerRepository.findById(id);
//
//        if (optionalPassenger.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Passenger passenger = optionalPassenger.get();
//
//        passenger.setFullName(updatedPassenger.getFullName());
//        passenger.setPersonalId(updatedPassenger.getPersonalId());
//        passenger.setChanged(Timestamp.valueOf(LocalDateTime.now()));
//
//        Passenger updated = passengerRepository.save(passenger);
//
//        return new ResponseEntity<>(updated, HttpStatus.OK);
//    }
}


