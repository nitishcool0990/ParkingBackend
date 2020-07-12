package com.vpark.vparkservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.vpark.vparkservice.dto.BonusDTO;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.service.ParkingBonusService;

@RestController
public class ParkingBonusController implements IParkingBonusController {
	
	@Autowired
	private ParkingBonusService parkingBonusService;

	@Override
	public ResponseEntity<EsResponse<List<BonusDTO>>> getBonusList(long userId) throws Exception {
		// TODO Auto-generated method stub
		 
		return  ResponseEntity.ok(this.parkingBonusService.getBonusList(userId));
	}

}
