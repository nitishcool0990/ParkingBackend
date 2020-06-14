package com.vpark.vparkservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vpark.vparkservice.entity.ParkingReviews;


@Repository
public interface IParkingReviewsRepository extends JpaRepository<ParkingReviews, Long>{

	@Query("select pr from ParkingReviews pr where pr.parkingLocId.id =?1 ")
	List <ParkingReviews>findAllByLocationId(long locId) ;
}
