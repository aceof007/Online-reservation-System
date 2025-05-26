// HotelImage.java - Complete Entity (copy this to replace your existing one)
package com.ORS.Online_reservation_System.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "hotel_image")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_image_id")
    private Long hotelImageId;

    @Column(name = "hotel_id", nullable = false)
    private Long hotelId;

    @NotBlank(message = "Image URL is required")
    @Size(max = 500, message = "Image URL cannot exceed 500 characters")
    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Size(max = 100, message = "Alt text cannot exceed 100 characters")
    @Column(name = "alt_text", length = 100)
    private String altText;

    @Builder.Default
    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false;

    @CreationTimestamp
    @Column(name = "uploaded_date", updatable = false)
    private LocalDateTime uploadedDate;

    // Relationship - if you have Hotel entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", insertable = false, updatable = false)
    private Hotel hotel;
}