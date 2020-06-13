package com.vpark.vparkservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLocationDto {

	private Object  latitude;
	private Object  longitude;
	private String parkingName;
	private String distance;
}
