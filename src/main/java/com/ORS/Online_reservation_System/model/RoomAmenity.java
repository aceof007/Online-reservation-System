
// RoomAmenity.java
package com.ORS.Online_reservation_System.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "room_amenity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomAmenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_amenity_id")
    private Long roomAmenityId;

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @Column(name = "amenity_id", nullable = false)
    private Long amenityId;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", insertable = false, updatable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "amenity_id", insertable = false, updatable = false)
    private Amenity amenity;
}