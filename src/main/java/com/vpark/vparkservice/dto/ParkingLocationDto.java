package com.vpark.vparkservice.dto;

import org.springframework.web.bind.annotation.PathVariable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLocationDto {
	//latitude,@PathVariable long longitude
	private Object  latitude;
	private Object  longitude;
	private String parkingName;
	private String distance;
}
