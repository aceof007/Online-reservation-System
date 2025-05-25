package com.ORS.Online_reservation_System.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(
        value = "Admin"
)
public class Admin extends User {

}
