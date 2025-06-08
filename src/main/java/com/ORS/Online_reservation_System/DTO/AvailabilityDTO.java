package com.ORS.Online_reservation_System.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AvailabilityDTO {
        private LocalDate checkInDate;
        private LocalDate checkOutDate;
        private int numberOfAdults;
        private int numberOfChildren;
        private Long roomId;
}
