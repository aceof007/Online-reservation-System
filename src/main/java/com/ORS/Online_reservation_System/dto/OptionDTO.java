package com.ORS.Online_reservation_System.dto;

import com.ORS.Online_reservation_System.model.OptionCategory;

public class OptionDTO {
    private Integer optionId;
    private String name;
    private String description;
    private Double price;
    private OptionCategory category;
    private Boolean availability;

    public OptionDTO() {
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public OptionCategory getCategory() {
        return category;
    }

    public void setCategory(OptionCategory category) {
        this.category = category;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }
}
