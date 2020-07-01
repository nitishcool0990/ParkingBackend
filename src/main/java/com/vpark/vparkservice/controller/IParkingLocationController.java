package com.vpark.vparkservice.controller;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.ParkingLocationDto;
import com.vpark.vparkservice.dto.ParkingReviewDTO;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.model.RequestAttribute;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Created by kalana.w on 5/22/2020.
 */
@RequestMapping("parking-locations/")
public interface IParkingLocationController {


    @PostMapping(value=IConstants.VERSION_1 + "/findLocByCoordinates",headers = IConstants.HEADER_VALUE)
    ResponseEntity<EsResponse<List<ParkingLocationDto>>> findLocationByCoordinates(@RequestParam("latitude") double  latitude,@RequestParam("longitude") double  longitude,@RequestParam("vehicleTypeId") int  vehicleTypeId  , @RequestAttribute("Id")  long userId );

    @GetMapping(IConstants.VERSION_1 + "/{id}/parking-details")
    ResponseEntity<EsResponse<?>> findAllLocationDetails(@PathVariable long id);

    @PatchMapping(IConstants.VERSION_1 + "/park-reviews/update")
    ResponseEntity<EsResponse<?>> patchParkReview( @RequestBody ParkingReviewDTO parkingReviewDto);

    @PostMapping(IConstants.VERSION_1 + "/park-reviews")
    ResponseEntity<EsResponse<?>> addParkReview( @RequestAttribute("Id")  long userId , @RequestBody ParkingReviewDTO parkingReviewDto);

    @DeleteMapping(IConstants.VERSION_1 + "/park-reviews/delete/{reviewId}")
    ResponseEntity<EsResponse<?>> deleteParkReview( @PathVariable long reviewId);

    @GetMapping(IConstants.VERSION_1 + "/park-reviews/{locId}")
    ResponseEntity<EsResponse<List<ParkingReviewDTO>>> findAllReviews(@PathVariable long locId   );

}
