package com.ORS.Online_reservation_System.model;

import java.util.Date;
import java.util.UUID;

public class Payment {
    private int paymentId;
    private int bookingId;
    private double amount;
    private Date paymentDate;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private String transactionId;
    private String billingAddress;

    // Constructors
    public Payment() {
    }

    public Payment(int paymentId, int bookingId, double amount, Date paymentDate,
                   PaymentMethod paymentMethod, PaymentStatus status, String transactionId,
                   String billingAddress) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.transactionId = transactionId != null ? transactionId : generateTransactionId();
        this.billingAddress = billingAddress;
    }

    // Getters and setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
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
                ", bookingId=" + bookingId +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", paymentMethod=" + paymentMethod +
                ", status=" + status +
                ", transactionId='" + transactionId + '\'' +
                '}';
    }
}