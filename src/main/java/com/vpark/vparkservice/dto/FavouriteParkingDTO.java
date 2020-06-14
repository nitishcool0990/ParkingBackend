package com.vpark.vparkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteParkingDTO {
	
	private long id ;
	private long locId ;
	private String parkingName ;
	private String opentime;
	private String closeTime ;
	private double bookingCharges ;
	
	

}
