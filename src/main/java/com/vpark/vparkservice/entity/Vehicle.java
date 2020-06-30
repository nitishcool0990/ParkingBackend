package com.vpark.vparkservice.entity;

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
//@JsonIgnoreProperties(value = {"user"}, allowSetters = true)
public class Vehicle extends Savable {

    @Column(name = "VEHICLE_NO", unique = true, nullable = false)
    private String vehicleNo;

    @ManyToOne(cascade = CascadeType.DETACH ,  fetch = FetchType.LAZY)
    @JoinColumn(name = "VEHICLE_TYPE_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_VEHICLETYPE2VEHICLE"))
    private VehicleType vehicleType;


    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_USER2VEHICLE"))
    private User user;
    
    @Column(name = "IS_DEFAULT", nullable = false)
    @Enumerated(EnumType.STRING)
    private IConstants.Default isDefault  = IConstants.Default.FALSE;
    
}
