package com.vpark.vparkservice.service;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.entity.ParkingLocation;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.repository.IParkingLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by kalana.w on 5/22/2020.
 */
@Service
public class ParkingLocationService {
    @Autowired
    private IParkingLocationRepository parkingLocationRepository;
    @Autowired
    private Environment ENV;

    public EsResponse<List<ParkingLocation>> findAllLocations(String region) {
        try {
            if (region == null || region.trim().isEmpty()) {
                return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.parkingLocationRepository.findAll(), this.ENV.getProperty("parking.location.search.success"));
            }
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.parkingLocationRepository.findAllByParkRegion(region), this.ENV.getProperty("parking.location.search.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("parking.location.search.failed"));
        }
    }

    public EsResponse<ParkingLocation> findLocationById(long id) {
        try {
            Optional<ParkingLocation> byId = this.parkingLocationRepository.findById(id);
            return byId.map(vehicle -> new EsResponse<>(IConstants.RESPONSE_STATUS_OK, vehicle, this.ENV.getProperty("parking.location.found")))
                    .orElseGet(() -> new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.location.not.found")));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.location.not.found"));
        }
    }

    public EsResponse<ParkingLocation> createNewLocation(ParkingLocation parkingLocation) {
        try {
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.parkingLocationRepository.save(parkingLocation), this.ENV.getProperty("parking.location.creation.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("parking.location.creation.failed"));
        }
    }

    public EsResponse<ParkingLocation> updateLocation(long id, ParkingLocation parkingLocation) {
        EsResponse<ParkingLocation> locationById = this.findLocationById(id);
        if (locationById.getStatus() == IConstants.RESPONSE_STATUS_ERROR) {
            return locationById;
        }
        try {
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.parkingLocationRepository.save(parkingLocation), this.ENV.getProperty("parking.location.update.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("parking.location.update.failed"));
        }
    }

    public EsResponse<?> deleteVehicle(long id) {
        EsResponse<ParkingLocation> locationById = this.findLocationById(id);
        if (locationById.getStatus() == IConstants.RESPONSE_STATUS_ERROR) {
            return locationById;
        }
        try {
            this.parkingLocationRepository.deleteById(id);
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("parking.location.delete.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("parking.location.delete.failed"));
        }
    }

}
