package com.avia.service;

import com.avia.model.entity.requests.PlaneTypeDto;
import com.avia.model.entity.PlaneType;

public interface PlaneTypeService {

    PlaneType createPlaneType(PlaneTypeDto planeTypeDto);

    PlaneType updatePlaneType(Integer id, PlaneTypeDto planeTypeDto);
}
