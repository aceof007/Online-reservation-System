// RoomTypeDTO.java
package com.ORS.Online_reservation_System.DTO;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypeDTO {
    private Long roomTypeId;
    private String typeName;
    private String description;
    private Boolean isActive;
    private Long roomCount;
}