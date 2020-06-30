package com.vpark.vparkservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vpark.vparkservice.dto.MyParkingHistoryDTO;
import com.vpark.vparkservice.entity.ParkBookingHistory;

@Repository
public interface IParkBookingHistoryRepository  extends JpaRepository<ParkBookingHistory, Long>{

	@Query("select pbh from ParkBookingHistory pbh where userId = ?1" )
	List<ParkBookingHistory>  findByUserId(long userId) ;
	
	@Query("select pbh from ParkBookingHistory pbh where parkingLocationId = ?1" )
	List<ParkBookingHistory>  findByParkingLocationId(long parkingLocId) ;
	
	  @Query("Select pbh .id ,    pbh.inTime ,  pbh.outTime , pbh.status  , pbh.bonusAmt+pbh.realAmt+pbh.depositAmt , pl.parkName,  v.vehicleNo  , v.vehicleType.vehicleName  ,  pbh.createdDate   "
	    		+ "  from  ParkBookingHistory pbh "
	    		+ " left join ParkingLocation pl on pbh.parkingLocationId  = pl.id "
	    		+ " left join Vehicle v on pbh.vehicleId = v.id  "
	    		+ " where pbh.userId = ?1  ")
	List<Object[]>  findParkingHistoryByUserId(long userId) ;
	
	
	@Query("select pbh from ParkBookingHistory pbh where id = ?1 and userId =?2" )
	Optional<ParkBookingHistory>  findByParkingBookingIdAndUserId(long parkBookId,long userId) ;
	
	
	
}
