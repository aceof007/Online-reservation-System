package com.ORS.Online_reservation_System.model;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class booking_option {
    // Getters and setters
    private int optionId;
    private String name;
    private String description;
    private double price;
    private OptionCategory category;
    private boolean availability;

    // Constructors
    public booking_option() {}

    public booking_option(int optionId, String name, String description, double price,
                          OptionCategory category, boolean availability) {
        this.optionId = optionId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.availability = availability;
    }

    // Business methods
    public double calculatePrice() {
        // This could be more complex in the future, e.g., based on options or discounts
        return price;
    }

    @Override
    public String toString() {
        return "Option{" +
                "optionId=" + optionId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", availability=" + availability +
                '}';
    }
}
