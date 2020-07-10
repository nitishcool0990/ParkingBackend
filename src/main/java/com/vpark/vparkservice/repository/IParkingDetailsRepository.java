package com.vpark.vparkservice.repository;

import com.vpark.vparkservice.entity.ParkingDetails;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by kalana.w on 5/22/2020.
 */
@Repository
public interface IParkingDetailsRepository extends JpaRepository<ParkingDetails, Long> {
	
	@Query("Select pd from ParkingDetails pd where pd.parkingLocation.id = ?1" )
	List <ParkingDetails>findByparkingLocationId(long locationId) ;
	
	@Query("Select pd from ParkingDetails pd where pd.parkingLocation.id = ?1 and pd.vehicleType.id = ?2" )
	Optional<ParkingDetails> findBylocationIdAndVehicleTypeId(long parkingLOCId,long vehicleTypeId);
}
