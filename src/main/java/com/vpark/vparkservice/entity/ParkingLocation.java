package com.vpark.vparkservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vpark.vparkservice.constants.IConstants;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by kalana.w on 5/17/2020.
 */
@Data
@Entity
@DynamicUpdate
@Table(name = "PARKING_LOCATIONS")
@JsonIgnoreProperties(value = {"parkingDetails"}, allowSetters = true)
public class ParkingLocation extends Savable {

    @Column(name = "PARK_NAME", unique = true, nullable = false)
    private String parkName;

    @Column(name = "PARK_ADDRESS", nullable = false)
    private String parkAddress;

    @Column(name = "PARK_REGION" )
    private String parkRegion;

    @Column(name = "LATITUDE", unique = true, nullable = false)
    private String latitude;

    @Column(name = "LONGITUDE", unique = true, nullable = false)
    private String longitude;
    
    @Column(name = "OPEN_TIME")
    private String openTime;

    @Column(name = "CLOSE_TIME")
    private String closeTime;

    @Column(name = "status", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private IConstants.Status status = IConstants.Status.INACTIVE;
    
    @ManyToOne(cascade = CascadeType.DETACH , fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_PAR_LOC2USER"))
    private User user; 

   
    @OneToMany(  cascade = CascadeType.ALL, orphanRemoval = true  )
    private List<ParkingDetails> parkingDetails = new ArrayList<ParkingDetails>();

    
    @ManyToOne(cascade = CascadeType.DETACH , fetch = FetchType.LAZY)
    @JoinColumn(name = "PARKING_TYPE_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_PARK_LOC2TYPE"))
    private ParkingType  parkingType ;
    
    
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "RATING")
    private double rating = 5;
    
    @Column(name = "ADVANCE_BOOKING_HR")
    private double advanceBookingHr = 1;
    
    
    @Column(name = "BOOKING_CANCEL_HR")
    private double bookingCancelHr = 0.5;
    
    @Lob
    @Column(name  = "photo")
    private byte[] photo; 
    
    
    
}
