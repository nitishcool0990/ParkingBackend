package com.vpark.vparkservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.BonusDTO;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.model.JwtResponse;
import com.vpark.vparkservice.model.RequestAttribute;

@RequestMapping("bonus/")
public interface IParkingBonusController {
	
	 @PostMapping(IConstants.VERSION_1 + "/bonusList")
	    ResponseEntity<EsResponse<List<BonusDTO>>> getBonusList(@RequestAttribute("Id") long id) throws Exception;

}
