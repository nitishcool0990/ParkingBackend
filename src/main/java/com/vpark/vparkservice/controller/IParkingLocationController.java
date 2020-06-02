package com.vpark.vparkservice.controller;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.entity.ParkingLocation;
import com.vpark.vparkservice.model.EsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by kalana.w on 5/22/2020.
 */
@RequestMapping("vehicles/")
public interface IParkingLocationController {

    @GetMapping(IConstants.VERSION_1)
    ResponseEntity<EsResponse<List<ParkingLocation>>> findAllLocations(
            @RequestParam(required = false) String parkRegion
    );

    @GetMapping(IConstants.VERSION_1 + "/{id}")
    ResponseEntity<EsResponse<ParkingLocation>> findLocationById(@PathVariable long id);

    @PostMapping(IConstants.VERSION_1)
    ResponseEntity<EsResponse<ParkingLocation>> createNewLocation(@RequestBody ParkingLocation parkingLocation);

    @PutMapping(IConstants.VERSION_1 + "/{id}")
    ResponseEntity<EsResponse<?>> updateLocation(@PathVariable long id, @RequestBody ParkingLocation parkingLocation);

    @DeleteMapping(IConstants.VERSION_1 + "/{id}")
    ResponseEntity<EsResponse<?>> deleteLocation(@PathVariable long id);
}
