package com.vpark.vparkservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vpark.vparkservice.entity.ParkingType;


@Repository
public interface IParkingTypeRepository  extends JpaRepository<ParkingType, Long>{

}
