// Amenity.java (Referenced by RoomAmenity)
package com.ORS.Online_reservation_System.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Entity
@Table(name = "amenity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "amenity_id")
    private Long amenityId;

    @NotBlank(message = "Amenity name is required")
    @Size(max = 100, message = "Amenity name cannot exceed 100 characters")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Column(name = "description", length = 500)
    private String description;

    @Size(max = 50, message = "Icon cannot exceed 50 characters")
    @Column(name = "icon", length = 50)
    private String icon;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private AmenityCategory category;

    // Add this for bidirectional Many-to-Many
    @ManyToMany(mappedBy = "amenities")
    private List<Hotel> hotels;

    @ManyToMany(mappedBy = "roomAmenities")
    private List<Room> rooms;

    @ManyToMany(mappedBy = "additionalAmenities")
    private List<Booking> bookings;


}