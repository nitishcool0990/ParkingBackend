package com.vpark.vparkservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vpark.vparkservice.entity.ParkTransHistory;


@Repository
public interface IParkTransHistoryRepository extends JpaRepository<ParkTransHistory, Long>{

}
