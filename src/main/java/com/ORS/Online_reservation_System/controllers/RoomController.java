// RoomController.java (CONTINUED)
package com.ORS.Online_reservation_System.controllers;

import com.ORS.Online_reservation_System.model.*;
import com.ORS.Online_reservation_System.services.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RoomController {

    private final RoomService roomService;

    // Room CRUD endpoints
    @PostMapping
    public ResponseEntity<Room> createRoom(@Valid @RequestBody Room room) {
        try {
            Room createdRoom = roomService.createRoom(room);
            return new ResponseEntity<>(createdRoom, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id)
                .map(room -> new ResponseEntity<>(room, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @Valid @RequestBody Room room) {
        try {
            Room updatedRoom = roomService.updateRoom(id, room);
            return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        try {
            roomService.deleteRoom(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Room query endpoints
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Room>> getRoomsByHotel(@PathVariable Long hotelId) {
        List<Room> rooms = roomService.getRoomsByHotel(hotelId);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/hotel/{hotelId}/available")
    public ResponseEntity<List<Room>> getAvailableRoomsByHotel(@PathVariable Long hotelId) {
        List<Room> rooms = roomService.getAvailableRoomsByHotel(hotelId);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/type/{roomTypeId}")
    public ResponseEntity<List<Room>> getRoomsByType(@PathVariable Long roomTypeId) {
        List<Room> rooms = roomService.getRoomsByType(roomTypeId);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/capacity/{minCapacity}")
    public ResponseEntity<List<Room>> getRoomsByCapacity(@PathVariable Integer minCapacity) {
        List<Room> rooms = roomService.getRoomsByCapacity(minCapacity);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/price")
    public ResponseEntity<List<Room>> getRoomsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        List<Room> rooms = roomService.getRoomsByPriceRange(minPrice, maxPrice);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/hotel/{hotelId}/room/{roomNumber}")
    public ResponseEntity<Room> getRoomByHotelAndRoomNumber(
            @PathVariable Long hotelId,
            @PathVariable String roomNumber) {
        return roomService.getRoomByHotelAndRoomNumber(hotelId, roomNumber)
                .map(room -> new ResponseEntity<>(room, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Room availability endpoints
    @PutMapping("/{id}/availability")
    public ResponseEntity<Void> setRoomAvailability(
            @PathVariable Long id,
            @RequestParam Boolean isAvailable) {
        try {
            roomService.setRoomAvailability(id, isAvailable);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/hotel/{hotelId}/available-count")
    public ResponseEntity<Long> getAvailableRoomCount(@PathVariable Long hotelId) {
        Long count = roomService.getAvailableRoomCount(hotelId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    // Room amenity endpoints
    @PostMapping("/{roomId}/amenities/{amenityId}")
    public ResponseEntity<Void> addAmenityToRoom(
            @PathVariable Long roomId,
            @PathVariable Long amenityId) {
        try {
            roomService.addAmenityToRoom(roomId, amenityId);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{roomId}/amenities/{amenityId}")
    public ResponseEntity<Void> removeAmenityFromRoom(
            @PathVariable Long roomId,
            @PathVariable Long amenityId) {
        roomService.removeAmenityFromRoom(roomId, amenityId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{roomId}/amenities")
    public ResponseEntity<List<Amenity>> getRoomAmenities(@PathVariable Long roomId) {
        List<Amenity> amenities = roomService.getRoomAmenities(roomId);
        return new ResponseEntity<>(amenities, HttpStatus.OK);
    }

    // Room image endpoints
    @PostMapping("/{roomId}/images")
    public ResponseEntity<Void> addImageToRoom(
            @PathVariable Long roomId,
            @RequestParam String imageUrl,
            @RequestParam(required = false) String altText,
            @RequestParam(defaultValue = "false") Boolean isPrimary) {
        try {
            roomService.addImageToRoom(roomId, imageUrl, altText, isPrimary);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<Void> removeImageFromRoom(@PathVariable Long imageId) {
        roomService.removeImageFromRoom(imageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{roomId}/images/{imageId}/primary")
    public ResponseEntity<Void> setPrimaryImage(
            @PathVariable Long roomId,
            @PathVariable Long imageId) {
        try {
            roomService.setPrimaryImage(roomId, imageId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}