package com.vpark.vparkservice.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Data;

@Data
@Entity
@DynamicUpdate
@Table(name = "FAVOURITE_PARKING")
public class FavouriteParking extends Savable {

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "PARK_LOC_ID")
	private ParkingLocation parkingLocation;
	
	@Column(name = "PARKING_NAME")
	private String parkingName ;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	private User user;

}
