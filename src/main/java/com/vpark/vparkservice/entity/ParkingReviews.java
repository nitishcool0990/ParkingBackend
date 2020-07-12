package com.vpark.vparkservice.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

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

	@ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_REVIEW2USER"))
    private User user; 

    @Column(name = "RATING")
    private Integer rating;

    @Column(name = "COMMENT")
    private String comment;
    
    @Column(name = "REPLY")
    private String reply;
    
    @ManyToOne( fetch = FetchType.LAZY)
   @JoinColumn(name = "PARK_LOC_ID")
    private ParkingLocation parkingLocId ;

}
