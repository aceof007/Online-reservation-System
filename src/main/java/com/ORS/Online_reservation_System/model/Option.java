package com.ORS.Online_reservation_System.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;
@Data
@Entity
@Table(name = "options")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer optionId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OptionCategory category;

    @Column(nullable = false)
    private Boolean availability;

    // Methods
    public OptionDetails getDetails() {
        return new OptionDetails(this.optionId, this.name, this.description, this.price, this.category);
    }

    public void updateDetails(OptionDetails optionDetails) {
        this.name = optionDetails.getName();
        this.description = optionDetails.getDescription();
        this.price = optionDetails.getPrice();
        this.category = optionDetails.getCategory();
    }

    public Double calculatePrice() {
        // Logic for calculating price (may include discounts, taxes, etc.)
        return this.price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return Objects.equals(optionId, option.optionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(optionId);
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
