
package com.ORS.Online_reservation_System.controllers;

import com.ORS.Online_reservation_System.model.RoomType;
import com.ORS.Online_reservation_System.services.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-types")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RoomTypeController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomType>> getAllRoomTypes() {
        List<RoomType> roomTypes = roomService.getAllRoomTypes();
        return new ResponseEntity<>(roomTypes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RoomType> createRoomType(@Valid @RequestBody RoomType roomType) {
        try {
            RoomType createdRoomType = roomService.createRoomType(roomType);
            return new ResponseEntity<>(createdRoomType, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomType> updateRoomType(
            @PathVariable Long id,
            @Valid @RequestBody RoomType roomType) {
        try {
            RoomType updatedRoomType = roomService.updateRoomType(id, roomType);
            return new ResponseEntity<>(updatedRoomType, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomType(@PathVariable Long id) {
        try {
            roomService.deleteRoomType(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
