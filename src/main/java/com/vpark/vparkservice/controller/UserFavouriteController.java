package com.vpark.vparkservice.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.FavouriteParkingDTO;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.service.FavouriteParkingLocService;

@RestController
public class UserFavouriteController implements IUserFavouriteController{

	@Autowired
    private Environment ENV;
	
	@Autowired
	private FavouriteParkingLocService  favParkingLocService ;
	
	
	@Override
	public ResponseEntity<EsResponse<?>> addFavouriteParkingLocation(long locId, long userId) {
		  if (locId <= 0 ) {
	            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
	        }
	        return ResponseEntity.ok(this.favParkingLocService.addFavouriteParkingLocation(locId , userId ));
	}

	
	@Override
	public ResponseEntity<EsResponse<List<FavouriteParkingDTO>>> findAllFavouriteParkingLocationById(long userId) {
	   
       return ResponseEntity.ok(this.favParkingLocService.findAllFavouriteParkingLocation(userId));
	}

	
	@Override
	public ResponseEntity<EsResponse<?>> deleteFavouriteLocation(long id) {
		if (id <= 0) {
		 return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
		}
		return ResponseEntity.ok(this.favParkingLocService.deleteFavouriteLocation(id));
	}
	
	

}
