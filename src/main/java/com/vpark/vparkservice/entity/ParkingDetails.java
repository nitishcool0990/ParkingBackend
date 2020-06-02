package com.vpark.vparkservice.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by kalana.w on 5/17/2020.
 */
@Data
@Entity
@DynamicUpdate
@Table(name = "PARKING_DETAILS")
public class ParkingDetails extends Savable {

    /*@Column(name = "PARK_NAME", unique = true, nullable = false)
    private String parkName;*/

}
