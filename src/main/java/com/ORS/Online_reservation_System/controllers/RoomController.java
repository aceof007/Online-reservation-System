package com.ORS.Online_reservation_System.Controllers;

import com.ORS.Online_reservation_System.model.Room;
import com.ORS.Online_reservation_System.model.SpecificRoom;
import com.ORS.Online_reservation_System.repositories.BookingRepository;
import com.ORS.Online_reservation_System.repositories.RoomRepository;
import com.ORS.Online_reservation_System.serviceimplementation.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
    @RequestMapping("/room")
    @RequiredArgsConstructor
    public class RoomController {

        private final RoomServiceImpl roomServiceImpl;
        private final RoomRepository roomRepository;
        private final BookingRepository bookingRepository;

    @GetMapping("/checkAvailability/{roomId}")
    public String checkRoomAvailability(
            @PathVariable Long roomId,
            @RequestParam("checkIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam("checkOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
            Model model) {

        Optional<Room> optionalRoom = roomRepository.findById(roomId);

        if (optionalRoom.isEmpty()) {
            model.addAttribute("availabilityMessage", "Room not found.");
            return "room_availability";
        }

        Room room = optionalRoom.get();

        List<SpecificRoom> availableSpecificRooms = room.getSpecific().stream()
                .filter(SpecificRoom::getIsAvailable) // Must be globally available
                .filter(specificRoom -> isRoomAvailableForDates(specificRoom, checkIn, checkOut))
                .toList();

        String message = !availableSpecificRooms.isEmpty()
                ? "Room is available from " + checkIn + " to " + checkOut + ". Available rooms: " + availableSpecificRooms.size()
                : "Room is not available during selected dates.";

        model.addAttribute("availabilityMessage", message);
        model.addAttribute("room", room);

        return "room_availability";
    }

    private boolean isRoomAvailableForDates(SpecificRoom specificRoom, LocalDate checkIn, LocalDate checkOut) {
        // Ensure the specific room is marked available at a base level
        if (!Boolean.TRUE.equals(specificRoom.getIsAvailable())) {
            return false;
        }

        // Check if the specific room has any overlapping bookings in the given range
        boolean hasConflict = bookingRepository.existsBookingForSpecificRoomAndDates(
                specificRoom.getSpecificRoomId(),
                checkIn,
                checkOut
        );

        // Room is available only if there are no conflicting bookings
        return !hasConflict;
    }



}
