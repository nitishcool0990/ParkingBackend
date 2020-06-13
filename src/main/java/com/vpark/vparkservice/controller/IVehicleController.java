package com.vpark.vparkservice.controller;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.VehicleDto;
import com.vpark.vparkservice.dto.VehicleTypeDTO;
import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.entity.Vehicle;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.model.RequestAttribute;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * Created by kalana.w on 5/22/2020.
 */
@RequestMapping("vehicles/")
public interface IVehicleController {

    @GetMapping(IConstants.VERSION_1 + "/all")
    ResponseEntity<EsResponse<List<VehicleDto>>> findAllVehicles(
            @RequestParam(required = false) String vehicleNo,
            @RequestParam(required = false) User user
    );

    @GetMapping(IConstants.VERSION_1 )
    ResponseEntity<EsResponse<List<VehicleDto>>> findVehicleByUserId(@RequestAttribute("Id")  long userId);
    
    @GetMapping(IConstants.VERSION_1 + "/type")
    public ResponseEntity<EsResponse<List<VehicleTypeDTO>>> findAllVehicleType() ;

    @PostMapping(IConstants.VERSION_1)
    ResponseEntity<EsResponse<Vehicle>> createNewVehicle(@RequestBody VehicleDto vehicleDto  , @RequestAttribute("Id")  long userId);

    @PutMapping(IConstants.VERSION_1 + "/update")
    ResponseEntity<EsResponse<?>> updateVehicle(@RequestBody VehicleDto vehicleDto);

    @DeleteMapping(IConstants.VERSION_1 + "/{id}/delete")
    ResponseEntity<EsResponse<?>> deleteVehicle(@PathVariable long id);
}
