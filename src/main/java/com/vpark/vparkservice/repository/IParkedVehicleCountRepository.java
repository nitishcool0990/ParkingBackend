package com.vpark.vparkservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vpark.vparkservice.entity.ParkedVehicleCount;

@Repository
public interface IParkedVehicleCountRepository extends JpaRepository<ParkedVehicleCount, Long>{

	ParkedVehicleCount findByParkingLocationIdAndVehicleTypeId(long locId  , long vehicleTypeId);
}
