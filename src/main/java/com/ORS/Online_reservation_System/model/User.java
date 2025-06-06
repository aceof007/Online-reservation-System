package com.ORS.Online_reservation_System.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "user",
        discriminatorType = DiscriminatorType.STRING
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;
    String username;
    String password;
    String email;
    String phoneNumber;
    String firstName;
    String lastName;
    Date registraionDate;
    String Status;
    String role;

}
