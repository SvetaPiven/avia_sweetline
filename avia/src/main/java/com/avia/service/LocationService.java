package com.avia.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final GeoApiContext geoApiContext;

    public String getAddressFromLatLng(Float latitude, Float longitude) throws Exception {
        GeocodingResult[] results = GeocodingApi.newRequest(geoApiContext)
                .latlng(new LatLng(latitude, longitude))
                .await();

        if (results.length > 0) {
            return results[0].formattedAddress;
        } else {
            throw new Exception("No results found");
        }
    }
}
