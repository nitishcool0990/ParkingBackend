package com.vpark.vparkservice.repository;

import com.vpark.vparkservice.entity.ParkingLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by kalana.w on 5/22/2020.
 */
@Repository
public interface IParkingLocationRepository extends JpaRepository<ParkingLocation, Long> {
    List<ParkingLocation> findAllByParkRegion(String region);
    
    @Query(value = "CALL `vPark`.`closest_parking`(:units,:latitude, :longitude, :distance, :limit);", nativeQuery = true)
    List<Object[]> getClosestParkingArea(String units,double  latitude,double  longitude, int distance,int limit  );
    
    @Query("Select pl from  ParkingLocation pl where pl.user.id = ?1" )
 	List<ParkingLocation> FindByUserId(long userId);
    

}
