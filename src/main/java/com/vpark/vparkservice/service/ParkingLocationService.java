package com.vpark.vparkservice.service;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.entity.ParkingDetails;
import com.vpark.vparkservice.entity.ParkingLocation;
import com.vpark.vparkservice.entity.ParkingReviews;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.repository.IParkingLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.location.search.failed"));
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
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.location.creation.failed"));
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
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.location.update.failed"));
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
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.location.delete.failed"));
        }
    }

    public EsResponse<?> patchLocationDetails(long id, ParkingDetails parkingDetail) {
        EsResponse<ParkingLocation> locationById = this.findLocationById(id);
        if (locationById.getStatus() == IConstants.RESPONSE_STATUS_ERROR) {
            return locationById;
        }
        try {
            ParkingLocation location = locationById.getData();
            Set<ParkingDetails> parkingDetails = location.getParkingDetails();
            Optional<ParkingDetails> pDetails = parkingDetails.stream().filter(pd -> pd.getId() == parkingDetail.getId()).findFirst();
            pDetails.ifPresent(parkingDetails1 -> {
                parkingDetails.remove(parkingDetails1);
                parkingDetails.add(parkingDetail);
            });
            location.setParkingDetails(parkingDetails);
            ParkingLocation save = this.parkingLocationRepository.save(location);
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, save, this.ENV.getProperty("parking.location.update.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.location.update.failed"));
        }
    }

    public EsResponse<?> findAllLocationDetails(long id) {
        EsResponse<ParkingLocation> locationById = this.findLocationById(id);
        if (locationById.getStatus() == IConstants.RESPONSE_STATUS_ERROR) {
            return locationById;
        }
        return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, locationById.getData().getParkingDetails(), this.ENV.getProperty("parking.review.search.success"));
    }

    public EsResponse<?> patchParkReviews(long id, ParkingReviews parkingReview) {
        EsResponse<ParkingLocation> locationById = this.findLocationById(id);
        if (locationById.getStatus() == IConstants.RESPONSE_STATUS_ERROR) {
            return locationById;
        }
        try {
            ParkingLocation location = locationById.getData();
            Set<ParkingReviews> parkingReviews = location.getParkingReviews();
            parkingReviews.add(parkingReview);
            location.setParkingReviews(parkingReviews);
            ParkingLocation save = this.parkingLocationRepository.save(location);
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, save, this.ENV.getProperty("parking.review.save.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.review.save.failed"));
        }
    }

    public EsResponse<?> updateParkReview(long id, long reviewId, ParkingReviews parkingReview) {
        EsResponse<ParkingLocation> locationById = this.findLocationById(id);
        if (locationById.getStatus() == IConstants.RESPONSE_STATUS_ERROR) {
            return locationById;
        }
        try {
            ParkingLocation location = locationById.getData();
            Set<ParkingReviews> parkingReviews = location.getParkingReviews();
            ParkingReviews existReview = parkingReviews.stream().filter(pr -> pr.getId() == reviewId).findFirst().orElse(null);
            if (existReview == null) {
                return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.review.not.found"));
            }
            parkingReviews.remove(existReview);
            parkingReviews.add(parkingReview);
            ParkingLocation save = this.parkingLocationRepository.save(location);
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, save, this.ENV.getProperty("parking.review.update.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.review.update.failed"));
        }
    }

    public EsResponse<?> deleteParkReview(long id, long reviewId) {
        EsResponse<ParkingLocation> locationById = this.findLocationById(id);
        if (locationById.getStatus() == IConstants.RESPONSE_STATUS_ERROR) {
            return locationById;
        }
        try {
            ParkingLocation location = locationById.getData();
            Set<ParkingReviews> parkingReviews = location.getParkingReviews();
            ParkingReviews existReview = parkingReviews.stream().filter(pr -> pr.getId() == reviewId).findFirst().orElse(null);
            if (existReview == null) {
                return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.review.not.found"));
            }
            parkingReviews.remove(existReview);
            ParkingLocation save = this.parkingLocationRepository.save(location);
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, save, this.ENV.getProperty("parking.review.delete.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.review.delete.failed"));
        }
    }

    public EsResponse<?> findAllReviews(long id, long userId) {
        EsResponse<ParkingLocation> locationById = this.findLocationById(id);
        if (locationById.getStatus() == IConstants.RESPONSE_STATUS_ERROR) {
            return locationById;
        }
        Set<ParkingReviews> parkingReviews = locationById.getData().getParkingReviews();
        if (userId > 0) {
            parkingReviews = parkingReviews.stream().filter(pr -> pr.getUserId() == userId).collect(Collectors.toSet());
        }
        return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, parkingReviews, this.ENV.getProperty("parking.review.search.success"));
    }

}
