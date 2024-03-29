package com.avia.controller.rest;

import com.avia.exception.EntityNotFoundException;
import com.avia.exception.ValidationException;
import com.avia.model.entity.Passenger;
import com.avia.model.request.PassengerRequest;
import com.avia.repository.PassengerRepository;
import com.avia.service.PassengerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
import java.util.Objects;
import java.util.Optional;

@RestController
@Tag(name = "PassengerRestController", description = "Passenger management methods")
@RequestMapping("/rest/passengers")
@RequiredArgsConstructor
public class PassengerRestController {

    private static final Logger log = Logger.getLogger(PassengerRestController.class);
    private final PassengerRepository passengerRepository;
    private final PassengerService passengerService;
    @Value("${passenger.page-capacity}")
    private Integer passengerPageCapacity;

    @GetMapping()
    public ResponseEntity<List<Passenger>> getAllPassengers() {

        return new ResponseEntity<>(passengerRepository.findAll(), HttpStatus.OK);
    }


    @GetMapping("/page/{page}")
    public ResponseEntity<Page<Passenger>> getAllPassengersWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, passengerPageCapacity, Sort.by("idPass").ascending());

        Page<Passenger> passengers = passengerRepository.findAll(pageable);

        if (passengers.hasContent()) {
            return new ResponseEntity<>(passengers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Passenger> createPassenger(@Valid @RequestBody PassengerRequest passengerRequest,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Passenger createdPassenger = passengerService.createPassenger(passengerRequest);

        return new ResponseEntity<>(createdPassenger, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable("id") Long id) {

        Optional<Passenger> passenger = passengerRepository.findById(id);

        return passenger.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("Passenger with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id,
                                                     @Valid @RequestBody PassengerRequest passengerRequest,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Passenger updatedPassenger = passengerService.updatePassenger(id, passengerRequest);

        return new ResponseEntity<>(updatedPassenger, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String deletePassenger(@PathVariable("id") Long id) {
        Optional<Passenger> passengerOptional = passengerRepository.findById(id);

        if (passengerOptional.isPresent()) {
            passengerRepository.deleteById(id);
            log.info("Passenger with id " + id + " deleted!");
            return "Passenger with id " + id + " deleted successfully.";
        } else {
            throw new EntityNotFoundException("Passenger with id " + id + " not found!");
        }
    }

    @PutMapping("/{id}/status")
    public String changeStatus(@PathVariable("id") Long id, @RequestParam("isDeleted") boolean isDeleted) {
        Optional<Passenger> passengerOptional = passengerRepository.findById(id);

        if (passengerOptional.isPresent()) {
            Passenger passenger = passengerOptional.get();
            passenger.setDeleted(isDeleted);
            passengerRepository.save(passenger);
            return "Status changed successfully";
        } else {
            throw new EntityNotFoundException("Passenger with id " + id + " not found!");
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
}
