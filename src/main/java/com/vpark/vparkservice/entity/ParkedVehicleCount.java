package com.vpark.vparkservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Data;

@Data
@Entity
@DynamicUpdate
@Table(name = "PARKED_VEHICLE_COUNT")
public class ParkedVehicleCount extends Savable{

	@Column(name = "PARKING_LOCATION_ID")
	private long  parkingLocationId ;
	
	@Column(name = "VEHICLE_TYPE_ID")
	private long  vehicleTypeId ;
	
	@Column(name = "TOTAL_COUNT")
	private Integer  totalCount ;
	
	@Column(name = "TOTAL_OCCUPIED")
	private Integer  totalOccupied = 0;
	
	@Column(name = "REMAINING_SPACE")
	private Integer  remainingSpace = 0;
	
	


}
