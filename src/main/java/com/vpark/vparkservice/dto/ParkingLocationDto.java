package com.vpark.vparkservice.dto;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
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
	private double canceBookingHr ;
	private double advanceBookingHr ;
	private byte[] image ;
	
	
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

	public ParkingLocationDto(Long bookingParkId, double hourlyRate, double monthlyRate, Double rating, String describe,String parkingName,
			 String openTime,String closeTime,String bookingRate , double cancelBookingHr , double advanceBookingHr , byte[] image ) {
		this.bookingParkId = bookingParkId;
		this.hourlyRate = hourlyRate;
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
	}
	
	public ParkingLocationDto(Long bookingParkId, Object latitude, Object longitude) {
		this.bookingParkId = bookingParkId;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	
	
	
	
}
