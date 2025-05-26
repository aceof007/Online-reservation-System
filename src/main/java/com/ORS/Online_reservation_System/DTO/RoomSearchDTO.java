
// RoomSearchDTO.java
package com.ORS.Online_reservation_System.DTO;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomSearchDTO {
    private Long hotelId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer minCapacity;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<Long> roomTypeIds;
    private List<Long> amenityIds;
    private Boolean availableOnly;
}