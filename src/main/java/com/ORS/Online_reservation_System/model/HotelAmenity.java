// HotelAmenity.java
package com.ORS.Online_reservation_System.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "hotel_amenity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelAmenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_amenity_id")
    private Long hotelAmenityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @NotBlank(message = "Amenity name is required")
    @Size(max = 100, message = "Amenity name cannot exceed 100 characters")
    @Column(name = "amenity", nullable = false, length = 100)
    private String amenity;
}