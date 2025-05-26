// Room.java
package com.ORS.Online_reservation_System.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @NotNull(message = "Hotel is required")
    @Column(name = "hotel_id")
    private Long hotelId;

    @NotNull(message = "Room type is required")
    @Column(name = "room_type_id")
    private Long roomTypeId;

    @NotBlank(message = "Room number is required")
    @Size(max = 10, message = "Room number cannot exceed 10 characters")
    @Column(name = "room_number", nullable = false, length = 10)
    private String roomNumber;

    @NotNull(message = "Price per night is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Invalid price format")
    @Column(name = "price_per_night", precision = 10, scale = 2)
    private BigDecimal pricePerNight;

    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 20, message = "Capacity cannot exceed 20")
    @Column(name = "capacity")
    private Integer capacity;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Column(name = "description", length = 1000)
    private String description;

    @Builder.Default
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = true;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", insertable = false, updatable = false)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_type_id", insertable = false, updatable = false)
    private RoomType roomType;

    @OneToMany(mappedBy = "roomId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<RoomAmenity> roomAmenities = new ArrayList<>();

    @OneToMany(mappedBy = "roomId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<RoomImage> roomImages = new ArrayList<>();
}
