package com.ORS.Online_reservation_System.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class LoginDTO {
    String username;
    String password;
    String email;
    String phoneNumber;
    String countryCode;
    String firstName;
    String lastName;

}
