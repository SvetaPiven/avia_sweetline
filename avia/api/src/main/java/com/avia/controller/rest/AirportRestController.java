package com.avia.controller.rest;

import com.avia.exception.EntityNotFoundException;
import com.avia.exception.ValidationException;
import com.avia.model.entity.Airport;
import com.avia.model.request.AirportRequest;
import com.avia.repository.AirportRepository;
import com.avia.service.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/airports")
public class AirportRestController {

    private final AirportRepository airportRepository;

    private final AirportService airportService;

    @Value("${airports.page-capacity}")
    private Integer airportsPageCapacity;

    @Operation(
            summary = "Get All Airports",
            description = "Find All airports without limitations",
            responses = {
                    @ApiResponse(
                            responseCode = "Ok",
                            description = "Successfully loaded airports",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Airport.class))
                            )
                    ),
                    @ApiResponse(responseCode = "INTERVAL_SERVER_ERROR", description = "Internal Server Error")
            }
    )
    @GetMapping()
    public ResponseEntity<List<Airport>> getAllAirlines() {

        return new ResponseEntity<>(airportRepository.findAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Airport Search with Pageable Params",
            description = "Load page by number with sort and offset params",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Successfully loaded Airports",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Airport.class)))
                    ),
                    @ApiResponse(
                            responseCode = "NOT_FOUND",
                            description = "Airports not found"
                    )
            }
    )
    @GetMapping("/page/{page}")
    public ResponseEntity<Map<String, Page<Airport>>> getAllAirportsWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, airportsPageCapacity, Sort.by("idAirport").ascending());

        Page<Airport> airports = airportRepository.findAll(pageable);

        if (airports.hasContent()) {
            Map<String, Page<Airport>> resultMap = Collections.singletonMap("result", airports);
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Airport> createAirline(@Valid @RequestBody AirportRequest airportRequest,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Airport createdAirline = airportService.createAirport(airportRequest);

        return new ResponseEntity<>(createdAirline, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirlineById(@PathVariable("id") Long id) {

        Optional<Airport> airline = airportRepository.findById(id);

        return airline.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("Airport with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Airport> updateAirline(@PathVariable Long id,
                                                 @Valid @RequestBody AirportRequest airportRequest,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Airport updatedAirport = airportService.updateAirport(id, airportRequest);

        return new ResponseEntity<>(updatedAirport, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String deleteTicket(@PathVariable("id") Long id) {
        Optional<Airport> airportOptional = airportRepository.findById(id);

        if (airportOptional.isPresent()) {
            airportRepository.deleteById(id);
            return "Airport with id " + id + " deleted successfully.";
        } else {
            throw new EntityNotFoundException("Airport with id " + id + " not found!");
        }
    }

    @PutMapping("/{id}/status")
    public String changeStatus(@PathVariable("id") Long id, @RequestParam("isDeleted") boolean isDeleted) {
        Optional<Airport> airportOptional = airportRepository.findById(id);
        if (airportOptional.isPresent()) {
            Airport airport = airportOptional.get();
            airport.setDeleted(isDeleted);
            airportRepository.save(airport);
            return "Status changed successfully";
        } else {
            throw new EntityNotFoundException("Airport with id " + id + " not found!");
        }
    }

    @GetMapping("/popular")
    public List<Airport> getPopularAirports() {
        return airportRepository.findPopularAirports();
    }
}


