package com.vpark.vparkservice.controller;

import java.math.BigInteger;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.ParkingLocationDto;
import com.vpark.vparkservice.model.EsResponse;

@RequestMapping("booking/")

public interface IParkingBookingController {
	
	 @GetMapping(value=IConstants.VERSION_1 + "/parkingInfo/{parkingId}/{vehicleTypeId}")
	    ResponseEntity<EsResponse<ParkingLocationDto>> getParkingInfo(@PathVariable long  parkingId,@PathVariable long vehicleTypeId);


}
