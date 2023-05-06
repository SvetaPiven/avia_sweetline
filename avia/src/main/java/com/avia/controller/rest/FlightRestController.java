package com.avia.controller.rest;

import com.avia.model.entity.Airport;
import com.avia.model.entity.Flight;
import com.avia.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/flights")
@RequiredArgsConstructor
public class FlightRestController {
    private final FlightRepository flightRepository;

    @GetMapping()
    public ResponseEntity<List<Flight>> getAllFlights() {

        List<Flight> flights = flightRepository.findAll();

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<Flight> createFlight(@RequestBody FlightDto flightDto) {
//
//        Flight build = Flight.builder()
//                .flightNumber(flightDto.getFlightNumber())
//                .idPlaneType(flightDto.getIdPlaneType())
//                .idDepartureAirport(flightDto.getIdDepartureAirport())
//                .idArrivalAirport(flightDto.getIdArrivalAirport())
//                .departureTime(flightDto.getDepartureTime())
//                .arrivalTime(flightDto.getArrivalTime())
//                .idFlightStatus(1L)
//                .created(Timestamp.valueOf(LocalDateTime.now()))
//                .isDeleted(false)
//                .build();
//
//        Flight createdFlight = flightRepository.save(build);
//
//        return new ResponseEntity<>(createdFlight, HttpStatus.CREATED);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable("id") Long id) {
        Optional<Flight> flight = flightRepository.findById(id);
        return flight.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Flight> updateFlight(@PathVariable("id") Long id, @RequestBody FlightDto flightDto) {
//        Optional<Flight> optionalFlight = flightRepository.findById(id);
//
//        if (optionalFlight.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Flight flight = optionalFlight.get();
//
//        flight.setFlightNumber(flightDto.getFlightNumber());
//        flight.setIdPlaneType(flightDto.getIdPlaneType());
//        flight.setIdDepartureAirport(flightDto.getIdDepartureAirport());
//        flight.setIdArrivalAirport(flightDto.getIdArrivalAirport());
//        flight.setDepartureTime(flightDto.getDepartureTime());
//        flight.setArrivalTime(flightDto.getArrivalTime());
//        flight.setIdFlightStatus(flightDto.getIdFlightStatus());
//        flight.setChanged(Timestamp.valueOf(LocalDateTime.now()));
//
//        Flight updated = flightRepository.save(flight);
//
//        return new ResponseEntity<>(updated, HttpStatus.OK);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable("id") Long id) {
        Optional<Flight> flightOptional = flightRepository.findById(id);

        if (flightOptional.isPresent()) {
            flightRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Flight>> getFlightByIdArrivalAirport(@RequestParam(value = "id") Airport id) {
        List<Flight> flights = flightRepository.findFlightByIdArrivalAirport(id);
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping("/search-by-number")
    public ResponseEntity<Flight> getFlightByNumber(@RequestParam(value = "number") String number) {
        Flight flight = flightRepository.findFlightByFlightNumber(number);
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    @GetMapping("/search-by-departure")
    public ResponseEntity<List<Flight>> getFlightsByDepartureTime() {
        List<Flight> flights = flightRepository.findFlightsByDepartureTimeAfter(Timestamp.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
}
