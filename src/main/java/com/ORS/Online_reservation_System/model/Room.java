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
@Table(
        name = "room",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"hotel_id", "room_number"})
        }
)

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @NotNull(message = "Room name is required")
    @Column(name = "room_type")
    private String roomName;

    @NotNull(message = "Price per night is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Invalid price format")
    @Column(name = "price_per_night", precision = 10, scale = 2)
    private BigDecimal pricePerNight;

    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Invalid price format")
    @Column(name = "previous_price_per_night", precision = 10, scale = 2)
    private BigDecimal previousPricePerNight;

    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 100, message = "Capacity cannot exceed 20")
    @Column(name = "total_Quantity")
    private Integer totalQuantity;

    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 100, message = "Capacity cannot exceed 20")
    @Column(name = "occupied_Quantity")
    private Integer occupiedQuantity;

    @Size(max = 100, message = "Bed type cannot exceed 100 characters")
    @Column(name = "bed_type", length = 100)
    private String bedType;

    @Min(value = 1, message = "Minimum occupancy is 1")
    @Max(value = 10, message = "Adults cannot exceed 10")
    @Column(name = "max_adults")
    private Integer maxAdults;

    @NotNull
    @Column(name = "prefix")
    private Integer prefix;

    @Min(value = 0, message = "Children cannot be negative")
    @Max(value = 10, message = "Children cannot exceed 10")
    @Column(name = "max_children")
    private Integer maxChildren;

    @DecimalMin(value = "1.0", message = "Room size must be at least 1")
    @Column(name = "room_size_m2")
    private Double roomSizeInSquareMeters;


    @Builder.Default
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = true;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<RateOption> rateOptions = new ArrayList<>();


    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;


    @ManyToMany
    @JoinTable(
            name = "room_amenity",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    @Builder.Default
    private List<Amenity> roomAmenities = new ArrayList<>();


    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<RoomImage> roomImages = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<SpecificRoom> specific = new ArrayList<>();
}
