package com.vpark.vparkservice.controller;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.VehicleDto;
import com.vpark.vparkservice.dto.VehicleTypeDTO;
import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.entity.Vehicle;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Created by kalana.w on 5/22/2020.
 */
@RestController
public class VehicleController  implements IVehicleController {

    @Autowired
    private Environment ENV;
    @Autowired
    private VehicleService vehicleService;

    @Override
    public ResponseEntity<EsResponse<List<VehicleDto>>> findAllVehicles(
            @RequestParam(required = false, defaultValue = "") String vehicleNo,
            @RequestParam User user) {
     
            return ResponseEntity.ok(this.vehicleService.findAllVehicles(vehicleNo, user));
     
     }

    @Override
    public ResponseEntity<EsResponse<List<VehicleDto>>> findVehicleByUserId( long userId) {
        if (userId <= 0) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
        return ResponseEntity.ok(this.vehicleService.findVehicleByUserId(userId));
    }
    
    @Override
    public ResponseEntity<EsResponse<List<VehicleTypeDTO>>> findAllVehicleType() {
       
        return ResponseEntity.ok(this.vehicleService.findAllVehicleType());
    }

    @Override
    public ResponseEntity<EsResponse<Vehicle>> createNewVehicle(@RequestBody VehicleDto vehicleDto , long userId ) {
        return ResponseEntity.ok(this.vehicleService.createNewVehicle(vehicleDto  , userId) );
    }

    @Override
    public ResponseEntity<EsResponse<?>> updateVehicle( @RequestBody VehicleDto vehicleDto) {
        if (vehicleDto.getId() <= 0) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
        return ResponseEntity.ok(this.vehicleService.updateVehicle( vehicleDto));

    }

    @Override
    public ResponseEntity<EsResponse<?>> deleteVehicle(@PathVariable long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
        return ResponseEntity.ok(this.vehicleService.deleteVehicle(id));
    }
}
