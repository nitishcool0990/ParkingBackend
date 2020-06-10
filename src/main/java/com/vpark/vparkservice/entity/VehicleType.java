package com.vpark.vparkservice.entity;

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
@Table(name = "VEHICLE_TYPE")
public class VehicleType extends Savable{
	

	@Column(name = "VEHICLE_NAME", unique = true, nullable = false )
    private String vehicleName;
	
	@Column(name = "IS_ACTIVE", nullable = false )
    @Enumerated(EnumType.STRING)
    private  IConstants.Default isActive  = IConstants.Default.TRUE;

}
