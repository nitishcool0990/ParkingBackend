package com.vpark.vparkservice.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpark.vparkservice.dto.AgentParkingLocationDTO;
import com.vpark.vparkservice.entity.ParkedVehicleCount;
import com.vpark.vparkservice.entity.ParkingDetails;
import com.vpark.vparkservice.entity.ParkingLocation;
import com.vpark.vparkservice.entity.ParkingType;
import com.vpark.vparkservice.entity.VehicleType;


@Component("ParkingLocMapper")
public class ParkingLocMapper {

	 @Autowired
	 private ModelMapper modelMapper;
	 
	public void mapAgentParkingLocToVo( AgentParkingLocationDTO parkingLocationDto , ParkingLocation parkingLocVo ){
		
		ParkingType parkingTypeVo = new ParkingType() ;
    	parkingTypeVo.setId(parkingLocationDto.getParkingTypeId());
    	parkingLocVo.setParkingType(parkingTypeVo);
    	
    	List<ParkingDetails> ParkingDetailsVos = parkingLocationDto.getParkingDetailsDtos() .stream()
    			  .map((parkingDetailsDto) -> {
    				  ParkingDetails  ParkingDetailsVo =  modelMapper.map(parkingDetailsDto , ParkingDetails.class);
    				  
    				  VehicleType  vehicleTypeVo = new VehicleType() ;
    				  vehicleTypeVo.setId(parkingDetailsDto.getVehicleTypeId());
    				  ParkingDetailsVo.setVehicleType(vehicleTypeVo);
    				 
    				  ParkingDetailsVo.setParkingLocation(parkingLocVo);
    				  
    				return ParkingDetailsVo ;
    			  })
    			  .collect(Collectors.toList());
    	
    	 parkingLocVo.setParkingDetails(ParkingDetailsVos);
	}
	
    public List<ParkedVehicleCount> createParkedVehicleVo( AgentParkingLocationDTO parkingLocationDto , long parkingLocId  ){
	  	
    	
    	List<ParkedVehicleCount> parkedVehicleCountVos = parkingLocationDto.getParkingDetailsDtos() .stream()
    			  .map((parkingDetailsDto) -> {
    				  
    				  ParkedVehicleCount parkedVehicleCountVo = new ParkedVehicleCount() ;
    				  parkedVehicleCountVo.setParkingLocationId(parkingLocId);
    				  parkedVehicleCountVo.setVehicleTypeId(parkingDetailsDto.getVehicleTypeId());
    				  parkedVehicleCountVo.setTotalCount(parkingDetailsDto.getCapacity());
    				  
    				return parkedVehicleCountVo ;
    			  })
    			  .collect(Collectors.toList());
    	
    	    return parkedVehicleCountVos ;
	}
}
