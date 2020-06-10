package com.vpark.vparkservice.repository;


import com.vpark.vparkservice.dto.VehicleDto;
import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kalana.w on 5/22/2020.
 */
@Repository
public interface IVehicleRepository extends JpaRepository<Vehicle, Long> {

    //List<VehicleDto> findByVehicleNoLikeAndVehicleTypeAndAndUserOrderByModifiedDateDesc(String vehicleNo,  User user);

    List<VehicleDto> findByVehicleNoLikeAndUserOrderByModifiedDateDesc(String vehicleNo, User user);

}
