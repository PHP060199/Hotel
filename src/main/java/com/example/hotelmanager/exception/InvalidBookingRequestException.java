package com.example.hotelmanager.exception;

public class InvalidBookingRequestException extends RuntimeException {
    public InvalidBookingRequestException (String message) {
        super(message);
    }
}
