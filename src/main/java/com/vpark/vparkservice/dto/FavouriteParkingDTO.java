package com.vpark.vparkservice.dto;



import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FavouriteParkingDTO {
	
	private long id ;
	private long locId ;
	private String parkingName ;
	private String openTime;
	private String closeTime ;
	private double bookingCharges ;
	
	

}
