package com.avia.service.impl;

import com.avia.model.dto.PlaneTypeDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.PlaneTypeMapper;
import com.avia.model.entity.PlaneType;
import com.avia.repository.PlaneTypeRepository;
import com.avia.service.PlaneTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaneTypeServiceImpl implements PlaneTypeService {

    private final PlaneTypeMapper planeTypeMapper;
    private final PlaneTypeRepository planeTypeRepository;

    @Override
    @Transactional
    public PlaneType createPlaneType(PlaneTypeDto planeTypeDto) {
        PlaneType planeType = planeTypeMapper.toEntity(planeTypeDto);
        return planeTypeRepository.save(planeType);
    }

    @Override
    @Transactional
    public PlaneType updatePlaneType(Integer id, PlaneTypeDto planeTypeDto) {
        PlaneType planeType = planeTypeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Plane type with id " + id + " not found"));
        planeTypeMapper.partialUpdate(planeTypeDto, planeType);
        return planeTypeRepository.save(planeType);
    }
}
