package com.ORS.Online_reservation_System.serviceimplementation;

import com.ORS.Online_reservation_System.model.Amenity;
import com.ORS.Online_reservation_System.repositories.AmenityRepository;
import com.ORS.Online_reservation_System.services.AmenityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AmenityServiceImplementation implements AmenityService {

    private final AmenityRepository amenityRepository;

    @Override
    public List<Amenity> getAllAmenities() {
        return amenityRepository.findAll();
    }

    @Override
    public Amenity createAmenity(Amenity amenity) {
        if (amenityRepository.existsByNameIgnoreCase(amenity.getName())) {
            throw new IllegalArgumentException("Amenity with this name already exists.");
        }
        return amenityRepository.save(amenity);
    }

    @Override
    public Amenity updateAmenity(Long id, Amenity updatedAmenity) {
        Amenity existingAmenity = amenityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Amenity not found"));

        // Prevent duplicate names
        if (!existingAmenity.getName().equalsIgnoreCase(updatedAmenity.getName()) &&
                amenityRepository.existsByNameIgnoreCase(updatedAmenity.getName())) {
            throw new IllegalArgumentException("Amenity with this name already exists.");
        }

        existingAmenity.setName(updatedAmenity.getName());
        return amenityRepository.save(existingAmenity);
    }

    @Override
    public void deleteAmenity(Long id) {
        if (!amenityRepository.existsById(id)) {
            throw new IllegalArgumentException("Amenity not found");
        }
        amenityRepository.deleteById(id);
    }
}

