package com.vpark.vparkservice.service;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.VehicleDto;
import com.vpark.vparkservice.dto.VehicleTypeDTO;
import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.entity.Vehicle;
import com.vpark.vparkservice.entity.VehicleType;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.repository.IVehicleRepository;
import com.vpark.vparkservice.repository.IVehicleTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kalana.w on 5/22/2020.
 */
@Service
public class VehicleService {
    @Autowired
    private IVehicleRepository vehicleRepository;
    
    @Autowired
    private IVehicleTypeRepository vehicleTypeRepository;
    
    @Autowired
    private Environment ENV;
    
    @Autowired
    private ModelMapper modelMapper;
    

  
    public EsResponse<List<VehicleDto>> findVehicleByUserId(long userId) {
        try {
        	List<Vehicle> vehicleVos = this.vehicleRepository.FindByUserId(userId);
        	
        	List<VehicleDto> vehicleDtos = vehicleVos .stream()
      			  .map((vehicleVo) -> {
      				VehicleDto  vehicleDto =  modelMapper.map(vehicleVo , VehicleDto.class);
      				return vehicleDto ;
      			  })
      			  .collect(Collectors.toList());
       
            return  new EsResponse<>(IConstants.RESPONSE_STATUS_OK, vehicleDtos , this.ENV.getProperty("vehicle.found"));
                  
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("vehicle.not.found"));
        }
    }
    

    
    public EsResponse<Vehicle> createNewVehicle(VehicleDto vehicleDto , long userId ) {
        try {
        	List<Vehicle> vehicleList =this.vehicleRepository.findAllVehicleNumber();
        	
        	for (Vehicle vehicle  : vehicleList) {
        		if(vehicle.getVehicleNo().equals(vehicleDto.getVehicleNo()))
        		   return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("vehicle.number.duplicate")); 
           }
        	
        	if(vehicleDto.getIsDefault().equalsIgnoreCase( "TRUE"))
			{  
        		List<Vehicle> vehicleVos = this.vehicleRepository.FindByUserId(userId);
				for (Vehicle vehicle : vehicleVos) {
					if (vehicle.getIsDefault().equals(IConstants.Default.TRUE) )
						return new EsResponse<>(IConstants.RESPONSE_DUPLICATE, this.ENV.getProperty("vehicle.default.exist"));
				}
			}
        	Vehicle vehicleVo = modelMapper.map(vehicleDto , Vehicle.class);
        
        	User user = new User() ;
        	user.setId(userId);
        	vehicleVo.setUser(user);
        	
        	vehicleRepository.save(vehicleVo) ;
        	
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("vehicle.creation.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("vehicle.creation.failed"));
        }
    }

    
    
    public EsResponse<List<VehicleTypeDTO>> findAllVehicleType() {
        try {
        	List<VehicleType> vehicleTypelist = this.vehicleTypeRepository.findByVehicleActive();
        	
        	List<VehicleTypeDTO> vehicleTypeDtos = vehicleTypelist .stream()
        			  .map(vehicleType -> modelMapper.map(vehicleType, VehicleTypeDTO.class))
        			  .collect(Collectors.toList());
        
        	 return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, vehicleTypeDtos, this.ENV.getProperty("vehicle.type.found"));
      
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("vehicle.type.not.found"));
        }
		
    }
   
    
    
    public EsResponse<?> updateVehicle( VehicleDto vehicleDto , long userId ) {
        try {
        	List<Vehicle> vehicleList = this.vehicleRepository.findAllVehicleNumber();
        	
        	for (Vehicle vehicle  : vehicleList) {
        		if(vehicle.getVehicleNo().equals(vehicleDto.getVehicleNo())  && vehicle.getId() != vehicleDto.getId() )
        		   return new EsResponse<>(IConstants.RESPONSE_DUPLICATE, this.ENV.getProperty("vehicle.number.duplicate")); 
           }
        	
        	if(vehicleDto.getIsDefault().equalsIgnoreCase( "TRUE")){
        		this.vehicleRepository.updateVehicleDefaultValueByUserId(userId  ,  IConstants.Default.FALSE);
        	}
        	
        	Vehicle vehicleVo = modelMapper.map(vehicleDto , Vehicle.class);
        	
        	User user = new User() ;
        	user.setId(userId);
        	vehicleVo.setUser(user);
        	
        	this.vehicleRepository.save(vehicleVo) ;
   
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK , this.ENV.getProperty("vehicle.update.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("vehicle.update.failed"));
        }
    }

    
    
    public EsResponse<?> deleteVehicle(long id) {
      
        try {
            this.vehicleRepository.deleteById(id);
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("vehicle.delete.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("vehicle.delete.failed"));
        }
    }


   

}
