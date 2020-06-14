package com.vpark.vparkservice.dto;

import org.springframework.web.bind.annotation.PathVariable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ParkingLocationDto {
	//latitude,@PathVariable long longitude
	private Object  latitude;
	private Object  longitude;
	private String parkingName;
	private String distance;
	public ParkingLocationDto(Object latitude, Object longitude, String parkingName, String distance) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.parkingName = parkingName;
		this.distance = distance;
	}
	
	
}
