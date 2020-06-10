package com.vpark.vparkservice.controller;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.VehicleDto;
import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.entity.Vehicle;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.model.RequestAttribute;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kalana.w on 5/22/2020.
 */
@RequestMapping("vehicles/")
public interface IVehicleController {

    @GetMapping(IConstants.VERSION_1)
    ResponseEntity<EsResponse<List<VehicleDto>>> findAllVehicles(
            @RequestParam(required = false) String vehicleNo,
            @RequestParam(required = false) User user
    );

    @GetMapping(IConstants.VERSION_1 + "/{id}")
    ResponseEntity<EsResponse<Vehicle>> findVehicleById(@PathVariable long id);

    @PostMapping(IConstants.VERSION_1)
    ResponseEntity<EsResponse<Vehicle>> createNewVehicle(@RequestBody Vehicle vehicle  , @RequestAttribute("Id")  long userId);

    @PutMapping(IConstants.VERSION_1 + "/{id}")
    ResponseEntity<EsResponse<?>> updateVehicle(@PathVariable long id, @RequestBody Vehicle vehicle);

    @DeleteMapping(IConstants.VERSION_1 + "/{id}")
    ResponseEntity<EsResponse<?>> deleteVehicle(@PathVariable long id);
}
