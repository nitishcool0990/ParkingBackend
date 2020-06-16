package com.vpark.vparkservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.MyParkingHistoryDTO;
import com.vpark.vparkservice.dto.ParkingLocationDto;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.model.RequestAttribute;

@RequestMapping("booking/")

public interface IParkingBookingController {
	
	 @GetMapping(value=IConstants.VERSION_1 + "/parkingInfo/{parkingId}/{vehicleTypeId}")
	    ResponseEntity<EsResponse<ParkingLocationDto>> getParkingInfo(@PathVariable long  parkingId,@PathVariable long vehicleTypeId);
	 
	 
	 @GetMapping(value=IConstants.VERSION_1+"/myParking")
	    ResponseEntity<EsResponse<List<MyParkingHistoryDTO>>> getUserParkingHistory(@RequestAttribute("Id")  long userId);


}
