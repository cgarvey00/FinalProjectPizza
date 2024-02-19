package com.finalprojectcoffee.entities;

public enum Status {
    Pending("Pending"),
    Accepted("Accepted"),
    Preparing("Preparing"),
    ReadForPick("Read for Pick"),
    OutForDelivery("Out of Delivery"),
    Delivered("Delivered"),
    Cancelled("Cancelled"),
    Failed("Failed"),
    WaitingForPickUp("Waiting for Pick Up"),
    EnRoute("En Route"),
    Returning("Returning"),
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
