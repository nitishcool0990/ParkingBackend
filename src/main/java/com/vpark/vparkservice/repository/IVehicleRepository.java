package com.vpark.vparkservice.repository;


import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.VehicleDto;
import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by kalana.w on 5/22/2020.
 */
@Repository
public interface IVehicleRepository extends JpaRepository<Vehicle, Long> {


    List<VehicleDto> findByVehicleNoLikeAndUserOrderByModifiedDateDesc(String vehicleNo, User user);

    @Query("Select v from  Vehicle v where v.user.id = ?1" )
	List<Vehicle> FindByUserId(long userId);
    
    @Query("Select v from  Vehicle v where v.user.id = ?1  and v.dispalyFlag = 'TRUE' " )
	List<Vehicle> FindActiveVehicleByUserId(long userId);
    
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("Update Vehicle v Set v.isDefault =?2 where v.user.id = ?1"   )
       void updateVehicleDefaultValueByUserId(long userId , IConstants.Default val);

    @Query(value = "select  * from vehicles " ,nativeQuery = true  )
    List<Vehicle> findAllVehicleNumber();
}
