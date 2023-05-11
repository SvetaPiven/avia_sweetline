package com.avia.controller.rest;

import com.avia.model.dto.PlaneTypeDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.model.entity.PlaneType;
import com.avia.repository.PlaneTypeRepository;
import com.avia.service.PlaneTypeService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/plane-types")
public class PlaneTypeRestController {

    private final PlaneTypeRepository planeTypeRepository;

    private final PlaneTypeService planeTypeService;

    private static final Logger log = Logger.getLogger(PlaneTypeRestController.class);

    @GetMapping()
    public ResponseEntity<List<PlaneType>> getAllPlaneTypes() {

        return new ResponseEntity<>(planeTypeRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Object> getAllPlaneTypesWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, 3, Sort.by("idPlaneTypes").ascending());

        Page<PlaneType> planeTypes = planeTypeRepository.findAll(pageable);

        if (planeTypes.hasContent()) {
            return new ResponseEntity<>(planeTypes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<PlaneType> createPlaneType(@RequestBody PlaneTypeDto planeTypeDto) {

        PlaneType createdPlaneType = planeTypeService.createPlaneType(planeTypeDto);

        return new ResponseEntity<>(createdPlaneType, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PlaneType> getPlaneTypeById(@PathVariable("id") Integer id) {

        Optional<PlaneType> planeType = planeTypeRepository.findById(id);

        return planeType.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("Plane type with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaneType> updatePlaneType(@PathVariable Integer id, @RequestBody PlaneTypeDto planeTypeDto) {

        PlaneType updatedPlaneType = planeTypeService.updatePlaneType(id, planeTypeDto);

        return new ResponseEntity<>(updatedPlaneType, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaneType(@PathVariable("id") Integer id) {

        Optional<PlaneType> planeTypeOptional = planeTypeRepository.findById(id);

        if (planeTypeOptional.isPresent()) {
            planeTypeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Plane type with id " + id + " not found!");
        }
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<Void> softDeletePlaneType(@PathVariable("id") Integer id) {

        Optional<PlaneType> planeTypeOptional = planeTypeRepository.findById(id);

        if (planeTypeOptional.isPresent()) {
            PlaneType planeType = planeTypeOptional.get();
            planeType.setIsDeleted(true);
            planeTypeRepository.save(planeType);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Plane type with id " + id + " not found!");
        }
    }
}
