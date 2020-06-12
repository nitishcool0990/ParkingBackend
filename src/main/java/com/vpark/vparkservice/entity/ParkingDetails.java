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

    @Column(name = "VEHICLE_TYPE_ID", nullable = false, length = 10)
    private int vehicleTypeId;

    @Column(name = "HOURLY_RATE")
    private double hourlyRate;

    @Column(name = "MONTHLY_RATE")
    private double monthlyRate;

    @Column(name = "CAPACITY")
    private String capacity;

    @Column(name = "STATUS", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private IConstants.Status status = IConstants.Status.INACTIVE;
    
   
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "PARK_LOC_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_REVIEW2PARK_LOC"))
     private ParkingLocation parkingLocId ;

}
