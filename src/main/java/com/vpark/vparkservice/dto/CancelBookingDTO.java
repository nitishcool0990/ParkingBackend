package com.vpark.vparkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelBookingDTO {
	
	private long bookingId ;
	
	private long  parkLocd ;
	
	private double  latitude ;
	
	private double  longitude;
	
}
