package com.vpark.vparkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckInAndCheckOutDTO {

	public long bookingId ;
	
	public long locationId ;
	
	public long vehicleTypeId ;
	
	public String vehicleNum;
	
	
}
