package com.avia.service;

import com.avia.model.entity.PlaneType;
import com.avia.model.request.PlaneTypeRequest;

public interface PlaneTypeService {

    PlaneType createPlaneType(PlaneTypeRequest planeTypeRequest);

    PlaneType updatePlaneType(Integer id, PlaneTypeRequest planeTypeRequest);
}
