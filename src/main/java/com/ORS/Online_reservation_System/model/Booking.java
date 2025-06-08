package com.ORS.Online_reservation_System.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User customer;

    @ManyToOne
    private SpecificRoom specificRoom;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private int numberOfAdults;
    private int numberOfChildren;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private BigDecimal totalPrice;

    @ManyToMany
    @JoinTable(
            name = "booking_additional_amenities",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private List<Amenity> additionalAmenities;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String specialRequest;

    private String estimatedArrivalTime;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

