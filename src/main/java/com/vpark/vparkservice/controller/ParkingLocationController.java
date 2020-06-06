package com.vpark.vparkservice.controller;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.entity.ParkingDetails;
import com.vpark.vparkservice.entity.ParkingLocation;
import com.vpark.vparkservice.entity.ParkingReviews;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.service.ParkingLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by kalana.w on 5/22/2020.
 */
@RestController
public class ParkingLocationController implements IParkingLocationController {

    @Autowired
    private Environment ENV;
    @Autowired
    private ParkingLocationService parkingLocationService;

    @Override
    public ResponseEntity<EsResponse<List<ParkingLocation>>> findAllLocations(
            @RequestParam(required = false, defaultValue = "") String parkRegion
    ) {
        return ResponseEntity.ok(this.parkingLocationService.findAllLocations(parkRegion));
    }

    @Override
    public ResponseEntity<EsResponse<ParkingLocation>> findLocationById(@PathVariable long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
        return ResponseEntity.ok(this.parkingLocationService.findLocationById(id));
    }

    @Override
    public ResponseEntity<EsResponse<ParkingLocation>> createNewLocation(@RequestBody ParkingLocation parkingLocation) {
        return ResponseEntity.ok(this.parkingLocationService.createNewLocation(parkingLocation));
    }

    @Override
    public ResponseEntity<EsResponse<?>> updateLocation(@PathVariable long id, @RequestBody ParkingLocation parkingLocation) {
        if (id <= 0 || id != parkingLocation.getId()) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
        return ResponseEntity.ok(this.parkingLocationService.updateLocation(id, parkingLocation));

    }

    @Override
    public ResponseEntity<EsResponse<?>> deleteLocation(@PathVariable long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
        return ResponseEntity.ok(this.parkingLocationService.deleteVehicle(id));
    }

    @Override
    public ResponseEntity<EsResponse<?>> patchParkingDetail(@PathVariable long id, @RequestBody ParkingDetails parkingDetails) {
        if (id <= 0 || parkingDetails.getId() <= 0) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
        return ResponseEntity.ok(this.parkingLocationService.patchLocationDetails(id, parkingDetails));
    }

    @Override
    public ResponseEntity<EsResponse<?>> findAllLocationDetails(@PathVariable long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
        return ResponseEntity.ok(this.parkingLocationService.findAllLocationDetails(id));
    }

    @Override
    public ResponseEntity<EsResponse<?>> patchParkReview(@PathVariable long id, @RequestBody ParkingReviews parkingReviews) {
        if (id <= 0) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
        return ResponseEntity.ok(this.parkingLocationService.patchParkReviews(id, parkingReviews));
    }

    @Override
    public ResponseEntity<EsResponse<?>> updateParkReview(@PathVariable long id, @PathVariable long reviewId, @RequestBody ParkingReviews parkingReview) {
        if (id <= 0 || reviewId <= 0 || reviewId != parkingReview.getId()) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
        return ResponseEntity.ok(this.parkingLocationService.updateParkReview(id, reviewId, parkingReview));
    }

    @Override
    public ResponseEntity<EsResponse<?>> deleteParkReview(@PathVariable long id, @PathVariable long reviewId) {
        if (id <= 0 || reviewId <= 0) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
        return ResponseEntity.ok(this.parkingLocationService.deleteParkReview(id, reviewId));
    }

    @Override
    public ResponseEntity<EsResponse<?>> findAllReviews(@PathVariable long id, @RequestParam(required = false, defaultValue = "0") long userId) {
        if (id <= 0) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
        return ResponseEntity.ok(this.parkingLocationService.findAllReviews(id, userId));
    }
}
