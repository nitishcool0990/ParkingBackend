package com.vpark.vparkservice.repository;

import com.vpark.vparkservice.entity.ParkingLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kalana.w on 5/22/2020.
 */
@Repository
public interface IParkingLocationRepository extends JpaRepository<ParkingLocation, Long> {
    List<ParkingLocation> findAllByParkRegion(String region);
}
