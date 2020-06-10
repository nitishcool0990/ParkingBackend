package com.vpark.vparkservice.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by kalana.w on 5/17/2020.
 */
@Data
@Entity
@DynamicUpdate
@Table(name = "PARKING_REVIEWS")
public class ParkingReviews extends Savable {

    @Column(name = "USER_ID", nullable = false)
    private long userId;

    @Column(name = "RATING")
    private Integer rating;

    @Column(name = "COMMENT")
    private String comment;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   @JoinColumn(name = "PARK_LOC_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_REVIEW2PARK_LOC"))
    private ParkingLocation parkingLocId ;

}
