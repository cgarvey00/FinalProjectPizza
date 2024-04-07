package com.finalprojectcoffee.entities;

public enum Status {
    Pending("Pending"),
    Accepted("Accepted"),
    Finished("Finished"),
    ReadForPick("Read for Pick"),
    OutForDelivery("Out for Delivery"),
    Delivered("Delivered"),
    Cancelled("Cancelled"),
    Failed("Failed"),

    Available("Available"),
    Unavailable("Unavailable"),
    Delivering("Delivering"),
    Paid("Paid"),
    PaymentFailed("Payment Failed"),
    Refunded("Refund");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
