package com.ORS.Online_reservation_System.DTO;

import com.ORS.Online_reservation_System.model.Amenity;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookingDTO {
    String firstName;
    String lastName;
    String email;
    String phone;
    String password;
    String specialRequest;
    String EstimatedArrivalTime;
    Long RoomId;
    List<Amenity> additionalAmenities;
     String cvv;
     String cardNumber;
     String cardName;
    int totalPrice;
    LocalDate checkInDate;
    LocalDate checkOutDate;

}
