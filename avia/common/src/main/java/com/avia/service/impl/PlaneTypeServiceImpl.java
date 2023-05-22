package com.avia.service.impl;

import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.PlaneTypeMapper;
import com.avia.model.entity.PlaneType;
import com.avia.model.request.PlaneTypeRequest;
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
    public PlaneType createPlaneType(PlaneTypeRequest planeTypeRequest) {
        PlaneType planeType = planeTypeMapper.toEntity(planeTypeRequest);
        return planeTypeRepository.save(planeType);
    }

    @Override
    @Transactional
    public PlaneType updatePlaneType(Integer id, PlaneTypeRequest planeTypeRequest) {
        PlaneType planeType = planeTypeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Plane type with id " + id + " not found"));
        planeTypeMapper.partialUpdate(planeTypeRequest, planeType);
        return planeTypeRepository.save(planeType);
    }
}
