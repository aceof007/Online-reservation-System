package com.ORS.Online_reservation_System.model;

import jakarta.persistence.*;
import lombok.Data;

import java.awt.print.Book;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @OneToOne
    private Booking booking;

    private double amount;

    private Date paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String transactionId;

    private String billingAddress;

    // Constructors
    public Payment() {
    }

    public Payment(int paymentId, Booking booking, double amount, Date paymentDate,
                   PaymentMethod paymentMethod, PaymentStatus status, String transactionId,
                   String billingAddress) {
        this.paymentId = paymentId;
        this.booking = booking;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.transactionId = transactionId != null ? transactionId : generateTransactionId();
        this.billingAddress = billingAddress;
    }

    // Business methods
    private String generateTransactionId() {
        // Generate a unique transaction ID
        return "TRX-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();
    }

    public boolean processPayment() {
        // This would contain logic to process the payment through a payment gateway
        // For this example, we'll just simulate a successful payment
        this.status = PaymentStatus.COMPLETED;
        if (this.transactionId == null) {
            this.transactionId = generateTransactionId();
        }
        return true;
    }

    public boolean refundPayment() {
        // Logic to process a refund
        if (this.status == PaymentStatus.COMPLETED) {
            this.status = PaymentStatus.REFUNDED;
            return true;
        }
        return false;
    }

    public boolean verifyPayment() {
        // Logic to verify payment with the payment gateway
        return this.status == PaymentStatus.COMPLETED;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", paymentMethod=" + paymentMethod +
                ", status=" + status +
                ", transactionId='" + transactionId + '\'' +
                '}';
    }
}