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
@Table(name = "PARKING_LOCATIONS")
public class ParkingLocation extends Savable {

    @Column(name = "PARK_NAME", unique = true, nullable = false)
    private String parkName;

    @Column(name = "PARK_ADDRESS", nullable = false)
    private String parkAddress;

    @Column(name = "PARK_REGION", nullable = false)
    private String parkRegion;

    @Column(name = "LATITUDE", nullable = false)
    private String latitude;

    @Column(name = "LONGITUDE", nullable = false)
    private String longitude;

    @Column(name = "STATUS", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private IConstants.Status status = IConstants.Status.INACTIVE;

}
