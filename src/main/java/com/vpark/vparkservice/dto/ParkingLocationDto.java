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
	private Long bookingParkId;
	private Object  latitude;
	private Object  longitude;
	private Double distance;
	private String color;
	private Double hourlyRate;
	private Double monthlyRate;
	private Double rating;
	private String describe;
	private String parkingName;
	private String openTime;
	private String closeTime;
	private String bookingRate;
	
	
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

	public ParkingLocationDto(Long bookingParkId, double hourlyRate, double monthlyRate,
			Double rating, String describe,String parkingName,String openTime,String closeTime,String bookingRate) {
		this.bookingParkId = bookingParkId;
		this.hourlyRate = hourlyRate;
		this.monthlyRate = monthlyRate;
		this.rating = rating;
		this.describe = describe;
		this.parkingName=parkingName;
		this.openTime=openTime;
		this.closeTime=closeTime;
		this.bookingRate = bookingRate;
	}
	
	
	
	
	
}
