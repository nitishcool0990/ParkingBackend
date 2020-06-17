package com.vpark.vparkservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vpark.vparkservice.entity.CashFreeTransHistory;

@Repository
public interface ICashFreeTransHistory extends JpaRepository<CashFreeTransHistory, Long>{

}
