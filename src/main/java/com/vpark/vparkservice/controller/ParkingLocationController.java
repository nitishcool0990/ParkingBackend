package com.vpark.vparkservice.controller;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.ParkingLocationDto;
import com.vpark.vparkservice.dto.ParkingReviewDTO;
import com.vpark.vparkservice.entity.ParkingDetails;
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
    public ResponseEntity<EsResponse<ParkingLocationDto>> findLocationByCoordinates(@RequestParam("latitude") double  latitude,@RequestParam("longitude") double  longitude) {
        return ResponseEntity.ok(this.parkingLocationService.findLocationByCooridates(latitude, longitude));
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
        return ResponseEntity.ok(this.parkingLocationService.findLocationDetailsById(id));
    }

    
    @Override
    public ResponseEntity<EsResponse<?>> patchParkReview(@PathVariable long id, @RequestBody ParkingReviews parkingReviews) {
        if (id <= 0) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
        return ResponseEntity.ok(this.parkingLocationService.patchParkReviews(id, parkingReviews));
    }

    @Override
    public ResponseEntity<EsResponse<?>> addParkReview( long userId , @RequestBody ParkingReviewDTO parkingReviewDto) {
        if (parkingReviewDto.getParkingLocId() <= 0 ) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
        return ResponseEntity.ok(this.parkingLocationService.addParkReview(userId ,  parkingReviewDto));
        
    }

    @Override
    public ResponseEntity<EsResponse<?>> deleteParkReview(@PathVariable long reviewId) {
        if ( reviewId <= 0) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
        return ResponseEntity.ok(this.parkingLocationService.deleteParkReview( reviewId));
        
    }

    @Override
    public ResponseEntity<EsResponse<List<ParkingReviewDTO>>> findAllReviews(@PathVariable long locId ) {
        if (locId <= 0) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
       return ResponseEntity.ok(this.parkingLocationService.findAllReviews(locId));
  
    }
}
