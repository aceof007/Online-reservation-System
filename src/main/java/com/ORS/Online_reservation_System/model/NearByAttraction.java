package com.ORS.Online_reservation_System.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "nearby_attraction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NearByAttraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attraction_id")
    private Long attractionId;

    @NotBlank(message = "Attraction name is required")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Distance or duration info is required")
    @Column(name = "distance_info", nullable = false)
    private String distanceInfo;  // e.g., "2 min walk", "20 min by metro"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}

