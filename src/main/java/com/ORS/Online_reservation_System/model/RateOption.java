package com.ORS.Online_reservation_System.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;


@Entity
@Table(name = "rate_option")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RateOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_option_id")
    private Long rateOptionId;

    @NotBlank(message = "Rate option name is required")
    @Size(max = 100)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "refundable", nullable = false)
    private boolean refundable;

    @Column(name = "prepayment_required", nullable = false)
    private boolean prepaymentRequired;

    @Min(0)
    @Max(100)
    @Column(name = "refund_percentage_after_deadline")
    private Integer refundPercentageAfterDeadline; // Nullable if not allowed

    @Column(name = "allow_late_cancellation", nullable = false)
    private boolean allowLateCancellation;

    @Column(name = "cancellation_deadline_days")
    private Integer cancellationDeadlineDays; // e.g., 2 means cancel up to 2 days before check-in

    @Column(name = "cancellation_fee_after_deadline")
    private BigDecimal cancellationFeeAfterDeadline;

    @Size(max = 500)
    @Column(name = "cancellation_policy", columnDefinition = "TEXT")
    private String cancellationPolicy;

    @Size(max = 1000)
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
}

