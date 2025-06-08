package com.ORS.Online_reservation_System.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "faq")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FAQ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faq_id")
    private Long id;

    @NotBlank(message = "Question is required")
    @Size(max = 300, message = "Question cannot exceed 300 characters")
    @Column(name = "question", nullable = false, length = 300)
    private String question;

    @NotBlank(message = "Answer is required")
    @Size(max = 1000, message = "Answer cannot exceed 1000 characters")
    @Column(name = "answer", nullable = false, length = 1000)
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;
}

