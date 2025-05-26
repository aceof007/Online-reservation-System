
// HotelController.java
package com.ORS.Online_reservation_System.controllers;

import com.ORS.Online_reservation_System.model.Hotel;
import com.ORS.Online_reservation_System.model.HotelAmenity;
import com.ORS.Online_reservation_System.model.HotelImage;
import com.ORS.Online_reservation_System.services.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HotelController {

    private final HotelService hotelService;

    // ===============================
    // BASIC HOTEL CRUD OPERATIONS
    // ===============================

    @PostMapping
    public ResponseEntity<?> createHotel(@Valid @RequestBody Hotel hotel) {
        try {
            // Check if hotel name already exists
            if (hotelService.hotelExistsByName(hotel.getName())) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Hotel with this name already exists"));
            }

            Hotel savedHotel = hotelService.saveHotel(hotel);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createSuccessResponse("Hotel created successfully", savedHotel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error creating hotel: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllActiveHotels() {
        try {
            List<Hotel> hotels = hotelService.findAllActiveHotels();
            return ResponseEntity.ok(createSuccessResponse("Hotels retrieved successfully", hotels));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error retrieving hotels: " + e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllHotels() {
        try {
            List<Hotel> hotels = hotelService.findAllHotels();
            return ResponseEntity.ok(createSuccessResponse("All hotels retrieved successfully", hotels));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error retrieving hotels: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHotelById(@PathVariable Long id) {
        try {
            return hotelService.findHotelById(id)
                    .map(hotel -> ResponseEntity.ok(createSuccessResponse("Hotel found", hotel)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error retrieving hotel: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHotel(@PathVariable Long id, @Valid @RequestBody Hotel hotel) {
        try {
            Hotel updatedHotel = hotelService.updateHotel(id, hotel);
            return ResponseEntity.ok(createSuccessResponse("Hotel updated successfully", updatedHotel));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error updating hotel: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHotel(@PathVariable Long id) {
        try {
            hotelService.deleteHotel(id);
            return ResponseEntity.ok(createSuccessResponse("Hotel deleted successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error deleting hotel: " + e.getMessage()));
        }
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateHotel(@PathVariable Long id) {
        try {
            hotelService.deactivateHotel(id);
            return ResponseEntity.ok(createSuccessResponse("Hotel deactivated successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error deactivating hotel: " + e.getMessage()));
        }
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<?> activateHotel(@PathVariable Long id) {
        try {
            hotelService.activateHotel(id);
            return ResponseEntity.ok(createSuccessResponse("Hotel activated successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error activating hotel: " + e.getMessage()));
        }
    }

    // ===============================
    // SEARCH OPERATIONS
    // ===============================

    @GetMapping("/search/city/{city}")
    public ResponseEntity<?> findHotelsByCity(@PathVariable String city) {
        try {
            List<Hotel> hotels = hotelService.findHotelsByCity(city);
            return ResponseEntity.ok(createSuccessResponse("Hotels found by city", hotels));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error searching hotels by city: " + e.getMessage()));
        }
    }

    @GetMapping("/search/state/{state}")
    public ResponseEntity<?> findHotelsByState(@PathVariable String state) {
        try {
            List<Hotel> hotels = hotelService.findHotelsByState(state);
            return ResponseEntity.ok(createSuccessResponse("Hotels found by state", hotels));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error searching hotels by state: " + e.getMessage()));
        }
    }

    @GetMapping("/search/rating/{rating}")
    public ResponseEntity<?> findHotelsByStarRating(@PathVariable Integer rating) {
        try {
            List<Hotel> hotels = hotelService.findHotelsByStarRating(rating);
            return ResponseEntity.ok(createSuccessResponse("Hotels found by star rating", hotels));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error searching hotels by rating: " + e.getMessage()));
        }
    }

    @GetMapping("/search/min-rating/{minRating}")
    public ResponseEntity<?> findHotelsByMinStarRating(@PathVariable Integer minRating) {
        try {
            List<Hotel> hotels = hotelService.findHotelsByMinStarRating(minRating);
            return ResponseEntity.ok(createSuccessResponse("Hotels found by minimum star rating", hotels));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error searching hotels by minimum rating: " + e.getMessage()));
        }
    }

    @GetMapping("/search/name")
    public ResponseEntity<?> searchHotelsByName(@RequestParam String name) {
        try {
            List<Hotel> hotels = hotelService.searchHotelsByName(name);
            return ResponseEntity.ok(createSuccessResponse("Hotels found by name search", hotels));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error searching hotels by name: " + e.getMessage()));
        }
    }

    @GetMapping("/search/amenity")
    public ResponseEntity<?> findHotelsWithAmenity(@RequestParam String amenity) {
        try {
            List<Hotel> hotels = hotelService.findHotelsWithAmenity(amenity);
            return ResponseEntity.ok(createSuccessResponse("Hotels found with amenity", hotels));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error searching hotels by amenity: " + e.getMessage()));
        }
    }

    // ===============================
    // AMENITY MANAGEMENT
    // ===============================

    @PostMapping("/{hotelId}/amenities")
    public ResponseEntity<?> addAmenityToHotel(@PathVariable Long hotelId, @RequestBody Map<String, String> request) {
        try {
            String amenityName = request.get("amenity");
            if (amenityName == null || amenityName.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Amenity name is required"));
            }

            HotelAmenity hotelAmenity = hotelService.addAmenityToHotel(hotelId, amenityName.trim());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createSuccessResponse("Amenity added successfully", hotelAmenity));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error adding amenity: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{hotelId}/amenities")
    public ResponseEntity<?> removeAmenityFromHotel(@PathVariable Long hotelId, @RequestParam String amenity) {
        try {
            hotelService.removeAmenityFromHotel(hotelId, amenity);
            return ResponseEntity.ok(createSuccessResponse("Amenity removed successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error removing amenity: " + e.getMessage()));
        }
    }

    @GetMapping("/{hotelId}/amenities")
    public ResponseEntity<?> getHotelAmenities(@PathVariable Long hotelId) {
        try {
            List<HotelAmenity> amenities = hotelService.getHotelAmenities(hotelId);
            return ResponseEntity.ok(createSuccessResponse("Hotel amenities retrieved successfully", amenities));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error retrieving hotel amenities: " + e.getMessage()));
        }
    }

    @GetMapping("/amenities/unique")
    public ResponseEntity<?> getAllUniqueAmenities() {
        try {
            List<String> amenities = hotelService.getAllUniqueAmenities();
            return ResponseEntity.ok(createSuccessResponse("All unique amenities retrieved successfully", amenities));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error retrieving unique amenities: " + e.getMessage()));
        }
    }

    // ===============================
    // IMAGE MANAGEMENT
    // ===============================

    @PostMapping("/{hotelId}/images")
    public ResponseEntity<?> addImageToHotel(@PathVariable Long hotelId, @RequestBody Map<String, Object> request) {
        try {
            String imageUrl = (String) request.get("imageUrl");
            String description = (String) request.get("description");
            Boolean isPrimary = (Boolean) request.get("isPrimary");

            if (imageUrl == null || imageUrl.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Image URL is required"));
            }

            HotelImage hotelImage = hotelService.addImageToHotel(hotelId, imageUrl.trim(), description, isPrimary);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createSuccessResponse("Image added successfully", hotelImage));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error adding image: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{hotelId}/images/{imageId}")
    public ResponseEntity<?> removeImageFromHotel(@PathVariable Long hotelId, @PathVariable Long imageId) {
        try {
            hotelService.removeImageFromHotel(hotelId, imageId);
            return ResponseEntity.ok(createSuccessResponse("Image removed successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error removing image: " + e.getMessage()));
        }
    }

    @GetMapping("/{hotelId}/images")
    public ResponseEntity<?> getHotelImages(@PathVariable Long hotelId) {
        try {
            List<HotelImage> images = hotelService.getHotelImages(hotelId);
            return ResponseEntity.ok(createSuccessResponse("Hotel images retrieved successfully", images));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error retrieving hotel images: " + e.getMessage()));
        }
    }

    @GetMapping("/{hotelId}/images/primary")
    public ResponseEntity<?> getPrimaryImage(@PathVariable Long hotelId) {
        try {
            return hotelService.getPrimaryImage(hotelId)
                    .map(image -> ResponseEntity.ok(createSuccessResponse("Primary image found", image)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error retrieving primary image: " + e.getMessage()));
        }
    }

    @PatchMapping("/{hotelId}/images/{imageId}/set-primary")
    public ResponseEntity<?> setPrimaryImage(@PathVariable Long hotelId, @PathVariable Long imageId) {
        try {
            hotelService.setPrimaryImage(hotelId, imageId);
            return ResponseEntity.ok(createSuccessResponse("Primary image set successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error setting primary image: " + e.getMessage()));
        }
    }

    // ===============================
    // VALIDATION ENDPOINTS
    // ===============================

    @GetMapping("/exists/name")
    public ResponseEntity<?> checkHotelExistsByName(@RequestParam String name) {
        try {
            boolean exists = hotelService.hotelExistsByName(name);
            Map<String, Object> result = new HashMap<>();
            result.put("exists", exists);
            return ResponseEntity.ok(createSuccessResponse("Name check completed", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error checking hotel name: " + e.getMessage()));
        }
    }

    @GetMapping("/{hotelId}/active")
    public ResponseEntity<?> isHotelActive(@PathVariable Long hotelId) {
        try {
            boolean isActive = hotelService.isHotelActive(hotelId);
            Map<String, Object> result = new HashMap<>();
            result.put("isActive", isActive);
            return ResponseEntity.ok(createSuccessResponse("Activity status retrieved", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error checking hotel status: " + e.getMessage()));
        }
    }

    // ===============================
    // UTILITY METHODS
    // ===============================

    private Map<String, Object> createSuccessResponse(String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        response.put("data", data);
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        response.put("data", null);
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }
}