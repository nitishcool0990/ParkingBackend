package com.vpark.vparkservice.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import com.vpark.vparkservice.constants.IConstants;
import lombok.Data;

@Data
@Entity
@DynamicUpdate
@Table(name = "PARK_BOOKING_HISTORY")
public class ParkBookingHistory extends Savable{
	
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "PARKING_DETAILS_ID")
	private ParkingDetails  parkingDetails ;
	
	@Column(name = "BOOKING_TYPE")
	private String bookingType ;
	
	@Column(name = "AMT")
	private double amt ;
	
	@Column(name = "CR_DR")
	private String cr_dr ;
	
	@Column(name = "IN_TIME")
	private String inTime ;
	
	@Column(name = "OUT_TIME")
	private String out_Time ;
	
	@Column(name = "remarks")
	private String remarks ;
	
	@Column(name = "status", nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private IConstants.ParkingStatus status = IConstants.ParkingStatus.RUNNING;

	
	


}
