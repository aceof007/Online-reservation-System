package com.ORS.Online_reservation_System.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import lombok.Data;

@Entity
@DiscriminatorValue(
        value = "Customer"
)
public class Customer extends User{

}
