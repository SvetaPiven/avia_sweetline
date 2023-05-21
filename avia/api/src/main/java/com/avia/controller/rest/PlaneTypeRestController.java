package com.avia.controller.rest;

import com.avia.exception.ValidationException;
import com.avia.model.entity.Passenger;
import com.avia.model.entity.PlaneType;
import com.avia.model.request.PlaneTypeRequest;
import com.avia.repository.PlaneTypeRepository;
import com.avia.service.PlaneTypeService;
import com.avia.exception.EntityNotFoundException;
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
@RequiredArgsConstructor
@RequestMapping("/rest/plane-types")
public class PlaneTypeRestController {

    private final PlaneTypeRepository planeTypeRepository;

    private final PlaneTypeService planeTypeService;

    @Value("${planeType.page-capacity}")
    private Integer planeTypePageCapacity;

    @GetMapping()
    public ResponseEntity<List<PlaneType>> getAllPlaneTypes() {

        return new ResponseEntity<>(planeTypeRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<PlaneType>> getAllPlaneTypesWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, planeTypePageCapacity, Sort.by("idPlaneTypes").ascending());

        Page<PlaneType> planeTypes = planeTypeRepository.findAll(pageable);

        if (planeTypes.hasContent()) {
            return new ResponseEntity<>(planeTypes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<PlaneType> createPlaneType(@Valid @RequestBody PlaneTypeRequest planeTypeRequest,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        PlaneType createdPlaneType = planeTypeService.createPlaneType(planeTypeRequest);

        return new ResponseEntity<>(createdPlaneType, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PlaneType> getPlaneTypeById(@PathVariable("id") Integer id) {

        Optional<PlaneType> planeType = planeTypeRepository.findById(id);

        return planeType.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("Plane type with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaneType> updatePlaneType(@PathVariable Integer id,
                                                     @Valid @RequestBody PlaneTypeRequest planeTypeRequest,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        PlaneType updatedPlaneType = planeTypeService.updatePlaneType(id, planeTypeRequest);

        return new ResponseEntity<>(updatedPlaneType, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String deletePlaneType(@PathVariable("id") Integer id) {
        Optional<PlaneType> planeTypeOptional = planeTypeRepository.findById(id);

        if (planeTypeOptional.isPresent()) {
            planeTypeRepository.deleteById(id);
            return "Plane type with ID " + id + " deleted successfully.";
        } else {
            throw new EntityNotFoundException("Plane type with ID " + id + " not found!");
        }
    }

    @PutMapping("/{id}/status")
    public String changeStatus(@PathVariable("id") Integer id, @RequestParam("isDeleted") boolean isDeleted) {
        Optional<PlaneType> planeTypeOptional = planeTypeRepository.findById(id);

        if (planeTypeOptional.isPresent()) {
            PlaneType planeType = planeTypeOptional.get();
            planeType.setDeleted(isDeleted);
            planeTypeRepository.save(planeType);
            return "Status changed successfully";
        } else {
            throw new EntityNotFoundException("Plane type with id " + id + " not found!");
        }
    }
}
