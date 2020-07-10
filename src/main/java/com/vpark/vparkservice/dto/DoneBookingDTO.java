package com.vpark.vparkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoneBookingDTO {

	private long  parkingId; 
	
	private double amount ;
	
	private long  vehicleId;
	
	private String  inTime ;
	
	private  String  outTime ;
	
	private boolean isMonthlyBooking ;
}
