package com.vpark.vparkservice.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ParkingBookingDTO {
	
	private long  parkingId;
	private double amount;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime fromDate; 

}
