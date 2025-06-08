package com.ORS.Online_reservation_System.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;


@Entity
@Table(name = "specific_room")
public class SpecificRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specific_room_id")
    private Long specificRoomId;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @NotBlank(message = "Room number is required")
    @Size(max = 10, message = "Room number cannot exceed 10 characters")
    @Column(name = "room_number", nullable = false, length = 10)
    private String roomNumber;

    @Column(name = "is_primary", nullable = false)
    private Boolean isAvailable = false;

}
