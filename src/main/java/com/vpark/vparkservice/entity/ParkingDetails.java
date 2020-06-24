package com.vpark.vparkservice.entity;

import com.vpark.vparkservice.constants.IConstants;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
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

    @Column(name = "HOURLY_RATE")
    private double hourlyRate;

    @Column(name = "MONTHLY_RATE")
    private double monthlyRate;

    @Column(name = "CAPACITY")
    private Integer capacity;

    @Column(name = "STATUS", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private IConstants.Status status = IConstants.Status.INACTIVE;
    
    @ManyToOne( cascade = CascadeType.DETACH )
    @JoinColumn(name = "PARK_LOC_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_DETAILS2PARK_LOC"))
     private ParkingLocation parkingLocation;
    
    @Column(name = "BOOKING_RATE")
    private double bookingRate  = 10 ;
    
    @Column(name = "AGENT_PERCENTAGE")
    private double agentPercentage  = 10 ;

}
