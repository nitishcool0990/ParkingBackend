package com.vpark.vparkservice.service;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.VehicleDto;
import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.entity.Vehicle;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.repository.IVehicleRepository;
import com.vpark.vparkservice.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by kalana.w on 5/22/2020.
 */
@Service
public class VehicleService {
    @Autowired
    private IVehicleRepository vehicleRepository;
    @Autowired
    private Environment ENV;

    public EsResponse<List<VehicleDto>> findAllVehicles(String vehicleNo, User user) {
        try {
            List<VehicleDto> list = this.vehicleRepository
                    .findByVehicleNoLikeAndUserOrderByModifiedDateDesc(Utility.queryLikeAny(vehicleNo) , user);
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, list, this.ENV.getProperty("vehicle.search.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("vehicle.search.failed"));
        }
    }

   

    public EsResponse<Vehicle> findVehicleById(long id) {
        try {
            Optional<Vehicle> byId = this.vehicleRepository.findById(id);
            return byId.map(vehicle -> new EsResponse<>(IConstants.RESPONSE_STATUS_OK, vehicle, this.ENV.getProperty("vehicle.found")))
                    .orElseGet(() -> new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("vehicle.not.found")));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("vehicle.not.found"));
        }
    }

    public EsResponse<Vehicle> createNewVehicle(Vehicle vehicle , long userId ) {
        try {
        	User user = new User() ;
        	user.setId(userId);
        	vehicle.setUser(user);
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.vehicleRepository.save(vehicle), this.ENV.getProperty("vehicle.creation.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("vehicle.creation.failed"));
        }
    }

    public EsResponse<Vehicle> updateVehicle(long id, Vehicle vehicle) {
        EsResponse<Vehicle> vehicleById = this.findVehicleById(id);
        if (vehicleById.getStatus() == IConstants.RESPONSE_STATUS_ERROR) {
            return vehicleById;
        }
        try {
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.vehicleRepository.save(vehicle), this.ENV.getProperty("vehicle.update.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("vehicle.update.failed"));
        }
    }

    public EsResponse<?> deleteVehicle(long id) {
        EsResponse<Vehicle> vehicleById = this.findVehicleById(id);
        if (vehicleById.getStatus() == IConstants.RESPONSE_STATUS_ERROR) {
            return vehicleById;
        }
        try {
            this.vehicleRepository.deleteById(id);
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("vehicle.delete.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("vehicle.delete.failed"));
        }
    }



}
