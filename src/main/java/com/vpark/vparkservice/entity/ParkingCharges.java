package com.vpark.vparkservice.entity;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Data;

@Data
@Entity
@DynamicUpdate
@Table(name = "PARKING_CHARGES")
public class ParkingCharges  extends Savable {

	
    @Column(name = "HOURS")
    private double hours= 1;
    
    @Column(name = "CHARGES")
    private double charges = 0;

    
    @ManyToOne( cascade = CascadeType.DETACH , fetch = FetchType.LAZY)
    @JoinColumn(name = "PARK_DETAILS_ID")
     private ParkingDetails parkingDetails;
	
}
