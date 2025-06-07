
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

}
