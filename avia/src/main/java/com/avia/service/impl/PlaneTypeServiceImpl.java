package com.avia.service.impl;

import com.avia.dto.requests.PlaneTypeDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.PlaneTypeMapper;
import com.avia.model.entity.Airline;
import com.avia.model.entity.PlaneType;
import com.avia.repository.PlaneTypeRepository;
import com.avia.service.PlaneTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaneTypeServiceImpl implements PlaneTypeService {

    private final PlaneTypeMapper planeTypeMapper;
    private final PlaneTypeRepository planeTypeRepository;

    @Override
    public PlaneType createPlaneType(PlaneTypeDto planeTypeDto) {
        PlaneType planeType = planeTypeMapper.toEntity(planeTypeDto);
        return planeTypeRepository.save(planeType);
    }

    @Override
    public PlaneType updatePlaneType(Integer id, PlaneTypeDto planeTypeDto) {
        PlaneType planeType = planeTypeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Airline with id " + id + " not found"));
        planeTypeMapper.partialUpdate(planeTypeDto, planeType);
        return planeTypeRepository.save(planeType);
    }
}
