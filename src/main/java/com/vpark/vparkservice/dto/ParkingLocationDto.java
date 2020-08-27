package com.vpark.vparkservice.dto;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.vpark.vparkservice.constants.IConstants;
import lombok.Data;
import java.math.BigInteger;
import java.util.TreeMap;

@Data
@JsonInclude(Include.NON_NULL)
public class ParkingLocationDto {
	
	  
	private BigInteger parkingId;
	private Long bookingParkId;
	private Object  latitude;
	private Object  longitude;
	private Double distance;
	private String color;
	private Double hours;
	private Double charges;
	private Double monthlyRate;
	private Double hourlyRate;
	private TreeMap<Double,Double> hourlyTimeSlot =null;
	private Double rating;
	private String describe;
	private String parkingName;
	private String openTime;
	private String closeTime;
	private String bookingRate;
	private Double canceBookingHr ;
	private Double advanceBookingHr ;
	private byte[] image ;
	private Integer remainingParking;
	private String chargesType ;
	private Double maxLimit;
	private boolean favParking  = false;
	
	
	public ParkingLocationDto(BigInteger parkingId, Object latitude, Object longitude, double distance, String color,
			double hours, double monthlyRate) {
		super();
		this.parkingId = parkingId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.distance = distance;
		this.color = color;
		this.hours = hours;
		this.monthlyRate = monthlyRate;
	}

	public ParkingLocationDto(Long bookingParkId,  double monthlyRate, Double rating, String describe,String parkingName,
			 String openTime,String closeTime,String bookingRate , double cancelBookingHr , double advanceBookingHr  ,  byte[] image ,TreeMap hourlyTimeSlot,int remainingParking) {
		this.bookingParkId = bookingParkId;
		this.monthlyRate = monthlyRate;
		this.rating = rating;
		this.describe = describe;
		this.parkingName=parkingName;
		this.openTime=openTime;
		this.closeTime=closeTime;
		this.bookingRate = bookingRate;
		this.advanceBookingHr = advanceBookingHr;
		this.canceBookingHr = cancelBookingHr ;
		this.image = image;
		this.hourlyTimeSlot =hourlyTimeSlot;
		this.remainingParking=remainingParking;
	}
	
	public ParkingLocationDto(Long bookingParkId, Object latitude, Object longitude) {
		this.bookingParkId = bookingParkId;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	
	
	
	
}
