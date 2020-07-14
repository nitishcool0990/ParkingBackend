package com.vpark.vparkservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Data;

@Data
@Entity
@DynamicUpdate
@Table(name = "PARKING_SEARCH_LOCATION")
public class ParkingSearchLocation extends Savable{
	

	@Column(name = "LATITUDE", nullable = false)
	  private Double latitude;

	 @Column(name = "LONGITUDE",  nullable = false)
	  private Double longitude;
	 
	 @Column(name = "USER_ID")
	 private long userId;
	 
	 @Column(name = "remarks")
	 private String remarks;

	
	 
}
