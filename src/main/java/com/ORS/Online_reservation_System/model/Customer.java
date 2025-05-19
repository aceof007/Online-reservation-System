package com.ORS.Online_reservation_System.model;

import jakarta.persistence.DiscriminatorValue;

@DiscriminatorValue(
        value = "Customer"
)
public class Customer extends User{

}
