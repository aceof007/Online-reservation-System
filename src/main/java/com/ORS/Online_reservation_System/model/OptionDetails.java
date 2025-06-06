package com.ORS.Online_reservation_System.model;

import lombok.Data;

@Data
public class OptionDetails {

    private Integer optionId;
    private String name;
    private String description;
    private Double price;
    private OptionCategory category;

    public OptionDetails(Integer optionId, String name, String description, Double price, OptionCategory category) {
    }
}
