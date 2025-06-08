// AmenityDTO.java
package com.ORS.Online_reservation_System.DTO;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmenityDTO {
    private Long amenityId;
    private String name;
    private String description;
    private String icon;
    private Boolean isActive;
}
