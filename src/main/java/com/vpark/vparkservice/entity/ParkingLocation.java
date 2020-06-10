package com.vpark.vparkservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vpark.vparkservice.constants.IConstants;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kalana.w on 5/17/2020.
 */
@Data
@Entity
@DynamicUpdate
@Table(name = "PARKING_LOCATIONS")
@JsonIgnoreProperties(value = {"parkingDetails", "parkingReviews"}, allowSetters = true)
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "PARKING_LOC_DETAILS",
            joinColumns = @JoinColumn(name = "PARK_LOC_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_PAK_LOC2DETAILS")),
            inverseJoinColumns = @JoinColumn(name = "DETAILS_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_DETAILS2PAK_LOC")))
    private Set<ParkingDetails> parkingDetails = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "PARKING_LOC_REVIEWS",
            joinColumns = @JoinColumn(name = "PARK_LOC_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_PAK_LOC2REVIEW")),
            inverseJoinColumns = @JoinColumn(name = "REVIEW_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_REVIEW2PAK_LOC")))
    private Set<ParkingReviews> parkingReviews = new HashSet<>();
    
   @ManyToOne(cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    @JoinColumn(name = "PARKING_TYPE_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_PARK_LOC2"))
    private ParkingType  parkingTypeId ;
}
