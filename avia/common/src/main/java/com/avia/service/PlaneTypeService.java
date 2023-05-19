package com.avia.service;

import com.avia.model.request.PlaneTypeRequest;
import com.avia.model.entity.PlaneType;

public interface PlaneTypeService {

    PlaneType createPlaneType(PlaneTypeRequest planeTypeRequest);

    PlaneType updatePlaneType(Integer id, PlaneTypeRequest planeTypeRequest);
}
