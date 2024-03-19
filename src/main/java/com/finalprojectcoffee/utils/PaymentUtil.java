package com.finalprojectcoffee.utils;
/**
 * @author cgarvey00
 */
public class PaymentUtil {
    public static boolean validatePaymentInfo(String payMethod, String cardNumber, String expiryDate, String cvv) {
        return payMethod != null && !payMethod.isEmpty() && cardNumber != null && !cardNumber.isEmpty() && expiryDate != null && !expiryDate.isEmpty()
                && cvv != null && !cvv.isEmpty();
    }
}
