package com.vpark.vparkservice.repository;

import com.vpark.vparkservice.entity.ParkingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kalana.w on 5/22/2020.
 */
@Repository
public interface IParkingDetailsRepository extends JpaRepository<ParkingDetails, Long> {
}
