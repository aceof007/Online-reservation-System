
// RoomDTO.java
package com.ORS.Online_reservation_System.DTO;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private Long roomId;
    private Long hotelId;
    private String hotelName;
    private Long roomTypeId;
    private String roomTypeName;
    private String roomNumber;
    private BigDecimal pricePerNight;
    private Integer capacity;
    private String description;
    private Boolean isAvailable;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<AmenityDTO> amenities;
    private List<RoomImageDTO> images;
    private String primaryImageUrl;
}
