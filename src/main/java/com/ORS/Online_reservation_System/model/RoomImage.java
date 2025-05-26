// RoomImage.java
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
@Table(name = "room_image")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_image_id")
    private Long roomImageId;

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @NotBlank(message = "Image URL is required")
    @Size(max = 500, message = "Image URL cannot exceed 500 characters")
    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Size(max = 100, message = "Alt text cannot exceed 100 characters")
    @Column(name = "alt_txt", length = 100)
    private String altText;

    @Builder.Default
    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false;

    @CreationTimestamp
    @Column(name = "uploaded_date", updatable = false)
    private LocalDateTime uploadedDate;

    // Relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", insertable = false, updatable = false)
    private Room room;
}
