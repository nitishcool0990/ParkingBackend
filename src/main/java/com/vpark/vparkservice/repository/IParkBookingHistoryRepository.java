package com.vpark.vparkservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.vpark.vparkservice.entity.ParkBookingHistory;

@Repository
public interface IParkBookingHistoryRepository  extends JpaRepository<ParkBookingHistory, Long>{

	@Query("select pbh from ParkBookingHistory pbh where userId = ?1" )
	List<ParkBookingHistory>  findByUserId(long userId) ;
	
	@Query("select pbh from ParkBookingHistory pbh where parkingLocationId = ?1" )
	List<ParkBookingHistory>  findByParkingLocationId(long parkingLocId) ;
	
	
}
