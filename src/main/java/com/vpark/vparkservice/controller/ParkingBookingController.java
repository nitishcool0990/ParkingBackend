package com.vpark.vparkservice.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.vpark.vparkservice.dto.ParkingLocationDto;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.service.ParkingBookingService;

@RestController
public class ParkingBookingController implements IParkingBookingController {
	
	@Autowired
	private ParkingBookingService parkingBookingService;

	@Override
	public ResponseEntity<EsResponse<ParkingLocationDto>> getParkingInfo(long parkingId, long vehicleTypeId) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(this.parkingBookingService.getParkingInfo(parkingId,vehicleTypeId));
	}

}
