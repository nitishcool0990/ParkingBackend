package com.vpark.vparkservice.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.FavouriteParkingDTO;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.model.RequestAttribute;


@RequestMapping("favourite/")
public interface IUserFavouriteController {

	 @PostMapping(IConstants.VERSION_1 + "/add/{locId}")
	  ResponseEntity<EsResponse<?>> addFavouriteParkingLocation(@PathVariable long locId    , @RequestAttribute("Id")  long userId);
	
	 @GetMapping(IConstants.VERSION_1 )
	 ResponseEntity<EsResponse<List<FavouriteParkingDTO>>> findAllFavouriteParkingLocationById(@RequestAttribute("Id")  long userId);
	 
	 @DeleteMapping(IConstants.VERSION_1 + "/delete/{id}")
	 ResponseEntity<EsResponse<?>> deleteFavouriteLocation(@PathVariable long id);
	 
	 
}
