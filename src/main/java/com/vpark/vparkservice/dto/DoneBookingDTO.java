package com.vpark.vparkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoneBookingDTO {

	private long  parkingLocId; 
	
	private double amount ;
	
	private long  vehicleId;
	
	private String  inTime ;
	
	private  String  outTime ;
	
	private boolean monthlyBookingFlag  ;
	
	private String  fromDate ; 
	
	private String bonusCode;
}
