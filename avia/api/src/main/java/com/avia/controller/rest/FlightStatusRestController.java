package com.avia.controller.rest;

import com.avia.model.entity.FlightStatus;
import com.avia.model.request.FlightStatusRequest;
import com.avia.repository.FlightStatusRepository;
import com.avia.service.FlightStatusService;
import com.avia.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/flight-statuses")
public class FlightStatusRestController {

    private final FlightStatusRepository flightStatusRepository;

    private final FlightStatusService flightStatusService;

    @Value("${flightStatus.page-capacity}")
    private Integer flightStatusPageCapacity;

    @GetMapping()
    public ResponseEntity<List<FlightStatus>> getAllFlightStatuses() {

        return new ResponseEntity<>(flightStatusRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Object> getAllFlightsStatusWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, flightStatusPageCapacity, Sort.by("idFlightStatus").ascending());

        Page<FlightStatus> flightStatuses = flightStatusRepository.findAll(pageable);

        if (flightStatuses.hasContent()) {
            return new ResponseEntity<>(flightStatuses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<FlightStatus> createFlightStatus(@RequestBody FlightStatusRequest flightStatusRequest) {

        FlightStatus createdFlightStatus = flightStatusService.createFlightStatus(flightStatusRequest);

        return new ResponseEntity<>(createdFlightStatus, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightStatus> getFlightStatusById(@PathVariable("id") Integer id) {

        Optional<FlightStatus> flightStatus = flightStatusRepository.findById(id);

        return flightStatus.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("Flight status with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightStatus> updateFlightStatus(@PathVariable Integer id, @RequestBody FlightStatusRequest flightStatusRequest) {

        FlightStatus updatedFlightStatus = flightStatusService.updateFlightStatus(id, flightStatusRequest);

        return new ResponseEntity<>(updatedFlightStatus, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlightStatus(@PathVariable("id") Integer id) {

        Optional<FlightStatus> flightStatusOptional = flightStatusRepository.findById(id);

        if (flightStatusOptional.isPresent()) {
            flightStatusRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Flight status with id " + id + " not found!");
        }
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<Void> deactivateFlightStatus(@PathVariable("id") Integer id) {
        Optional<FlightStatus> flightStatusOptional = flightStatusRepository.findById(id);

        if (flightStatusOptional.isPresent()) {
            FlightStatus flightStatus = flightStatusOptional.get();
            flightStatus.setDeleted(true);
            flightStatusRepository.save(flightStatus);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Flight status with id " + id + " not found!");
        }
    }
}

