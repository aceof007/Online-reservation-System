// RoomImageDTO.java
package com.ORS.Online_reservation_System.DTO;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomImageDTO {
    private Long roomImageId;
    private Long roomId;
    private String imageUrl;
    private String altText;
    private Boolean isPrimary;
    private LocalDateTime uploadedDate;
}