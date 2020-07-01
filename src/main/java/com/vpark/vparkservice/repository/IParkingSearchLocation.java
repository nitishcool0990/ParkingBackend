package com.vpark.vparkservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vpark.vparkservice.entity.ParkingSearchLocation;

@Repository
public interface IParkingSearchLocation extends JpaRepository<ParkingSearchLocation, Long>{

}
