package com.vpark.vparkservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vpark.vparkservice.entity.SmsText;


@Repository
public interface ISmsTextRepository extends JpaRepository<SmsText, Integer>{

}
