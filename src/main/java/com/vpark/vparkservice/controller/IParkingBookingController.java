package com.vpark.vparkservice.controller;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.data.redis.core.TimeToLive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.ParkingLocationDto;
import com.vpark.vparkservice.dto.PaymentDTO;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.model.RequestAttribute;

@RequestMapping("booking/")

public interface IParkingBookingController {
	
	 @GetMapping(value=IConstants.VERSION_1 + "/parkingInfo/{parkingId}/{vehicleTypeId}")
	    ResponseEntity<EsResponse<ParkingLocationDto>> getParkingInfo(@PathVariable long  parkingId,@PathVariable long vehicleTypeId);
	 
	 @PostMapping(value=IConstants.VERSION_1 + "/initBooking")
	    ResponseEntity<EsResponse<PaymentDTO>> initBooking(@RequestParam("parkingId") long  parkingId,@RequestAttribute("Id") long id,@RequestParam("amount") double amount,@RequestParam("fromTime")  String  fromDate,@RequestParam("toTime") String  toDate  );
	 
	 @PostMapping(value=IConstants.VERSION_1 + "/addAmount")
	 ResponseEntity<EsResponse<PaymentDTO>> addBookingAmount(@RequestParam("parkingId") long  parkingId,@RequestAttribute("Id") long id,@RequestParam("amount") double amount );

	 @PostMapping(value=IConstants.VERSION_1 + "/doneBooking")
	 ResponseEntity<EsResponse<ParkingLocationDto>> doneBooking(@RequestParam("parkingId") long  parkingId,@RequestAttribute("Id") long id,@RequestParam("amount") double amount,@RequestParam("forBook") boolean forBook );



}
