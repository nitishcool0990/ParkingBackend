package com.vpark.vparkservice.controller;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.ParkingLocationDto;
import com.vpark.vparkservice.entity.ParkingDetails;
import com.vpark.vparkservice.entity.ParkingLocation;
import com.vpark.vparkservice.entity.ParkingReviews;
import com.vpark.vparkservice.model.EsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by kalana.w on 5/22/2020.
 */
@RequestMapping("parking-locations/")
public interface IParkingLocationController {

    @GetMapping(IConstants.VERSION_1)
    ResponseEntity<EsResponse<List<ParkingLocation>>> findAllLocations(
            @RequestParam(required = false) String parkRegion
    );

    @PostMapping(value=IConstants.VERSION_1 + "/findLocByCoordinates",headers = IConstants.HEADER_VALUE)
    ResponseEntity<EsResponse<ParkingLocationDto>> findLocationByCoordinates(@RequestParam("latitude") double  latitude,@RequestParam("longitude") double  longitude);

    @PostMapping(IConstants.VERSION_1)
    ResponseEntity<EsResponse<ParkingLocation>> createNewLocation(@RequestBody ParkingLocation parkingLocation);

    @PutMapping(IConstants.VERSION_1 + "/{id}")
    ResponseEntity<EsResponse<?>> updateLocation(@PathVariable long id, @RequestBody ParkingLocation parkingLocation);

    @DeleteMapping(IConstants.VERSION_1 + "/{id}")
    ResponseEntity<EsResponse<?>> deleteLocation(@PathVariable long id);

    @PatchMapping(IConstants.VERSION_1 + "/{id}/parking-details")
    ResponseEntity<EsResponse<?>> patchParkingDetail(@PathVariable long id, @RequestBody ParkingDetails parkingDetails);

    @GetMapping(IConstants.VERSION_1 + "/{id}/parking-details")
    ResponseEntity<EsResponse<?>> findAllLocationDetails(@PathVariable long id);

    @PatchMapping(IConstants.VERSION_1 + "/{id}/park-reviews")
    ResponseEntity<EsResponse<?>> patchParkReview(@PathVariable long id, @RequestBody ParkingReviews parkingReviews);

    @PutMapping(IConstants.VERSION_1 + "/{id}/park-reviews/{reviewId}")
    ResponseEntity<EsResponse<?>> updateParkReview(@PathVariable long id, @PathVariable long reviewId, @RequestBody ParkingReviews parkingReview);

    @DeleteMapping(IConstants.VERSION_1 + "/{id}/park-reviews/{reviewId}")
    ResponseEntity<EsResponse<?>> deleteParkReview(@PathVariable long id, @PathVariable long reviewId);

    @GetMapping(IConstants.VERSION_1 + "/{id}/park-reviews")
    ResponseEntity<EsResponse<?>> findAllReviews(@PathVariable long id, @RequestParam(required = false, defaultValue = "0") long userId);

}
