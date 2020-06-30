package com.vpark.vparkservice.repository;

import com.vpark.vparkservice.entity.ParkingLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Created by kalana.w on 5/22/2020.
 */
@Repository
public interface IParkingLocationRepository extends JpaRepository<ParkingLocation, Long> {
    List<ParkingLocation> findAllByParkRegion(String region);
    
    @Query(value = "SELECT pl.id,latitude,longitude, ROUND((CASE ?1 WHEN 'miles' THEN 3959 ELSE 6371 END * ACOS( COS( RADIANS(?2) ) * COS( RADIANS(latitude) ) * COS( RADIANS(longitude) - RADIANS(?3)) + SIN(RADIANS(?2)) * SIN( RADIANS(latitude)))  ), 3) AS distance,"
    		+ "  pt.color,pd.hourly_rate,pd.monthly_rate "
    		+ "  FROM parking_locations pl "
    		+ " LEFT JOIN parking_type pt ON pl.parking_type_id = pt.id "
    		+ " LEFT JOIN `parking_details` pd ON pl.id= pd.park_loc_id "
    		+ " WHERE vehicle_type_id = ?6 HAVING distance <= ?4 ORDER BY distance ASC   LIMIT ?5", nativeQuery = true)
    List<Object[]> getClosestParkingArea(String units,double  latitude,double  longitude, int distance,int limit ,int vehicleTypeId );
    
    @Query("Select pl from  ParkingLocation pl where pl.user.id = ?1" )
 	List<ParkingLocation> FindByUserId(long userId);
    
    @Query("Select pl.id,pl.parkName,pl.openTime,pl.closeTime,pl.description,pl.rating,pd.hourlyRate,pd.monthlyRate,pd.bookingRate , pl.advanceBookingHr , pl.bookingCancelHr , pl.photo"
    		+ "  from  ParkingLocation pl "
    		+ " left join ParkingDetails pd on pl.id= pd.parkingLocation "
    		+ " left join VehicleType vt on pd.vehicleType = vt.id  "
    		+ " where pl.id = ?1 and  vt.id =?2 and pl.status='ACTIVE'")
    List<Object[]> getParkingInfo(long parkingId,long vehicleTypeId);
    
    @Query("select  pl from  ParkingLocation pl where pl.id = ?1")
    Optional<ParkingLocation> findByParkingLocId(long parkingLOCId);
    
    

}
