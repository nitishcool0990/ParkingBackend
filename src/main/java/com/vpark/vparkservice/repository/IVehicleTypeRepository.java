package com.vpark.vparkservice.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.vpark.vparkservice.entity.VehicleType;

@Repository
public interface IVehicleTypeRepository extends JpaRepository<VehicleType, Long>{

	@Query("select vt from VehicleType vt where vt.isActive = 'TRUE' " )
	List<VehicleType> findByVehicleActive();

}

	