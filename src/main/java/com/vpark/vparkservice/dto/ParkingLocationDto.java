package com.vpark.vparkservice.dto;


import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigInteger;

@Data
@JsonInclude(Include.NON_NULL)
public class ParkingLocationDto {
	
	  
	private BigInteger parkingId;
	private Object  latitude;
	private Object  longitude;
	private double distance;
	private String color;
	private double hourlyRate;
	private double monthlyRate;
	private Integer rating;
	private String describe;
	
	public ParkingLocationDto(BigInteger parkingId, Object latitude, Object longitude, double distance, String color,
			double hourlyRate, double monthlyRate) {
		super();
		this.parkingId = parkingId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.distance = distance;
		this.color = color;
		this.hourlyRate = hourlyRate;
		this.monthlyRate = monthlyRate;
	}
	
	
	
}
