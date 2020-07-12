package com.vpark.vparkservice.entity;



import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import com.vpark.vparkservice.constants.IConstants;
import lombok.Data;

@Data
@Entity
@DynamicUpdate
@Table(name = "PARK_BOOKING_HISTORY")
public class ParkBookingHistory extends Savable{
	

	@Column(name = "USER_ID")
	private long userId;
	
	@Column(name = "PARKING_LOCATION_ID")
	private long  parkingLocationId ;
	
	@Column(name = "PARKING_DETAILS_ID")
	private long  parkingDetailsId ;
	
	@Column(name = "VEHICLE_ID")
	private long  vehicleId ;
	
	@Column(name = "BOOKING_TYPE")
	private String bookingType ;
	
	@Column(name = "BONUS_AMT")
	private double bonusAmt =0;
	
	@Column(name = "REAL_AMT")
	private double realAmt =0;
	
	@Column(name = "DEPOSIT_AMT")
	private double depositAmt =0;
	
	@Column(name = "CR_DR")
	private String cr_dr ;
	
	@Column(name = "IN_TIME")
	private String inTime = null ;
	
	@Column(name = "OUT_TIME")
	private String outTime = null ;
	
	@Column(name = "remarks")
	private String remarks ;
	
	@Column(name = "FROM_DATE", nullable = false )
     private LocalDate fromDate = LocalDate.now();

    @Column(name = "END_DATE", nullable = false )
     private LocalDate endDate = LocalDate.now();
	
	@Column(name = "status", nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private IConstants.ParkingStatus status = IConstants.ParkingStatus.RUNNING;

	
 


}
