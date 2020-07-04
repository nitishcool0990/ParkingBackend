package com.vpark.vparkservice.entity;

import com.vpark.vparkservice.constants.IConstants;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Created by kalana.w on 5/17/2020.
 */
@Data
@Entity
@DynamicUpdate
@Table(name = "PARKING_DETAILS")
public class ParkingDetails extends Savable {

	@ManyToOne
    @JoinColumn(name = "VEHICLE_TYPE_ID")
    private VehicleType vehicleType;

    @Column(name = "CAPACITY")
    private Integer capacity;
    
    @Column(name = "MONTHLY_RATE")
    private double monthlyRate =0 ;
    
    @Column(name = "NIGHT_CHARGES")
    private double nightCharges = 0;
    
    @Column(name = "CHARGES_TYPE", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private IConstants.ChargesType chargesType = IConstants.ChargesType.PERHOUR ;
    
    
    @Column(name = "STATUS", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private IConstants.Status status = IConstants.Status.INACTIVE;
    
    
    @ManyToOne( cascade = CascadeType.DETACH , fetch = FetchType.LAZY)
    @JoinColumn(name = "PARK_LOC_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_DETAILS2PARK_LOC"))
     private ParkingLocation parkingLocation;
    
    
    @OneToMany(  cascade = CascadeType.ALL, orphanRemoval = true  )
    private List<ParkingCharges> parkingCharges = new ArrayList<ParkingCharges>();
    

    @Column(name = "BOOKING_RATE")
    private double bookingRate  = 10 ;
    
    
    @Column(name = "AGENT_PERCENTAGE")
    private double agentPercentage  = 10 ;

}
