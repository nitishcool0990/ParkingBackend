package com.vpark.vparkservice.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Data;

@Data
@Entity
@DynamicUpdate
@Table(name = "PARKING_TYPE")
public class ParkingType extends Savable{

	
	@Column(name = "PARKING_TYPE", nullable = false )
    private  String parkingType ;
	
	@Column(name = "color", nullable = false , unique = true)
	private String color ;
	
}
