// ===============================
// 1. ENTITIES/MODELS
// ===============================

// Hotel.java
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
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "hotel")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    private Long hotelId;

    @NotBlank(message = "Hotel name is required")
    @Size(max = 100, message = "Hotel name cannot exceed 100 characters")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Address is required")
    @Column(name = "address", nullable = false, columnDefinition = "LONGTEXT")
    private String address;

    @Size(max = 50, message = "City name cannot exceed 50 characters")
    @Column(name = "city", length = 50)
    private String city;

    @Size(max = 50, message = "State name cannot exceed 50 characters")
    @Column(name = "state", length = 50)
    private String state;

    @Size(max = 50, message = "Country name cannot exceed 50 characters")
    @Column(name = "country", length = 50)
    private String country;

    @Size(max = 20, message = "Postal code cannot exceed 20 characters")
    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Size(max = 20, message = "Phone number cannot exceed 20 characters")
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Email(message = "Please provide a valid email address")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    @Column(name = "email", length = 100)
    private String email;

    @Size(max = 200, message = "Website URL cannot exceed 200 characters")
    @Column(name = "website", length = 200)
    private String website;

    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;

    @Min(value = 1, message = "Star rating must be at least 1")
    @Max(value = 5, message = "Star rating cannot exceed 5")
    @Column(name = "star_rating")
    private Integer starRating;

    @Size(max = 255, message = "Contact info cannot exceed 255 characters")
    @Column(name = "contact_info", length = 255)
    private String contactInfo;

    @Column(name = "check_in_time", columnDefinition = "TIME DEFAULT '15:00'")
    private LocalTime checkInTime;

    @Column(name = "check_out_time", columnDefinition = "TIME DEFAULT '11:00'")
    private LocalTime checkOutTime;

    @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
    @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;

    @Builder.Default
    @Column(name = "is_active", columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Size(max = 8, message = "Managed by field cannot exceed 8 characters")
    @Column(name = "managed_by", length = 8)
    private String managedBy;

    // Relationships
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HotelAmenity> hotelAmenities;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HotelImage> hotelImages;
}