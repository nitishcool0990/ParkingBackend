package com.vpark.vparkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingTypeDTO {

	private String parkingType ;
	private long id ;
	private String color ;
}
