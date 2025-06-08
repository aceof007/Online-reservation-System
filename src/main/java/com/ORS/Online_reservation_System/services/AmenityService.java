package com.ORS.Online_reservation_System.services;

import com.ORS.Online_reservation_System.model.Amenity;

import java.util.List;

public interface AmenityService {
    List<Amenity> getAllAmenities();
    Amenity createAmenity(Amenity amenity);
    Amenity updateAmenity(Long id, Amenity amenity);
    void deleteAmenity(Long id);
}
