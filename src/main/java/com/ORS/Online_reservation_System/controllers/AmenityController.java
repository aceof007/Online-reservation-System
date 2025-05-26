// AmenityController.java
package com.ORS.Online_reservation_System.controllers;

import com.ORS.Online_reservation_System.model.Amenity;
import com.ORS.Online_reservation_System.services.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/amenities")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AmenityController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<Amenity>> getAllAmenities() {
        List<Amenity> amenities = roomService.getAllAmenities();
        return new ResponseEntity<>(amenities, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Amenity> createAmenity(@Valid @RequestBody Amenity amenity) {
        try {
            Amenity createdAmenity = roomService.createAmenity(amenity);
            return new ResponseEntity<>(createdAmenity, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Amenity> updateAmenity(
            @PathVariable Long id,
            @Valid @RequestBody Amenity amenity) {
        try {
            Amenity updatedAmenity = roomService.updateAmenity(id, amenity);
            return new ResponseEntity<>(updatedAmenity, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmenity(@PathVariable Long id) {
        try {
            roomService.deleteAmenity(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}