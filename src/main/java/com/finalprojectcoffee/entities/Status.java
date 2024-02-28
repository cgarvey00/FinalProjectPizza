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
    WaitingForPickUp("Waiting for Pick Up"),

    Available("Available"),
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
