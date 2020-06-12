package com.vpark.vparkservice.entity;

import com.vpark.vparkservice.constants.IConstants;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "capacity")
    private String capacity;

    @Column(name = "USER_STATUS", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private IConstants.Status status = IConstants.Status.INACTIVE;

}
