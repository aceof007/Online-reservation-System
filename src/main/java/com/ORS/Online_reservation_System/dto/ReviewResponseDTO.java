package com.ORS.Online_reservation_System.dto;

public class ReviewResponseDTO {

    private String response;

    public ReviewResponseDTO() {
    }

    public ReviewResponseDTO(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
