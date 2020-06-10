package com.vpark.vparkservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vpark.vparkservice.constants.IConstants;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by kalana.w on 5/22/2020.
 */
@Data
@Entity
@DynamicUpdate
@Table(name = "VEHICLES")
@JsonIgnoreProperties(value = {"user"}, allowSetters = true)
public class Vehicle extends Savable {

    @Column(name = "VEHICLE_NO", unique = true, nullable = false)
    private String vehicleNo;

    @Column(name = "VEHICLE_TYPE", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private IConstants.VehicleType vehicleType;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_USER2VEHICLE"))
    private User user;
}
