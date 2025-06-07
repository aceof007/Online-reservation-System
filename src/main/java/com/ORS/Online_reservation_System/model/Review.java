package com.ORS.Online_reservation_System.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Objects;
@Data
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer hotelId;

    @Column(nullable = false)
    private Integer bookingId;

    @Column(nullable = false)
    private Float rating;

    @Column(length = 1000)
    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date date;

    private String response;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewStatus status;



    // Methods
    public Review submitReview() {
        this.date = new Date();
        this.status = ReviewStatus.SUBMITTED;
        return this;
    }

    public void updateReview(String comment, Float rating) {
        this.comment = comment;
        this.rating = rating;
        this.date = new Date();
    }

    public boolean deleteReview() {
        this.status = ReviewStatus.DELETED;
        return true;
    }

    public boolean approveReview() {
        if (this.status == ReviewStatus.SUBMITTED || this.status == ReviewStatus.PENDING) {
            this.status = ReviewStatus.APPROVED;
            return true;
        }
        return false;
    }

    public boolean respondToReview(String response) {
        if (this.status == ReviewStatus.APPROVED) {
            this.response = response;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(reviewId, review.reviewId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId);
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", userId=" + userId +
                ", hotelId=" + hotelId +
                ", bookingId=" + bookingId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                ", response='" + response + '\'' +
                ", status=" + status +
                '}';
    }

}
