package com.vpark.vparkservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vpark.vparkservice.entity.ParkBookingHistory;

@Repository
public interface IParkBookingHistoryRepository  extends JpaRepository<ParkBookingHistory, Long>{

}
