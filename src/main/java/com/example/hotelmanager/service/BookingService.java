package com.example.hotelmanager.service;

import com.example.hotelmanager.DTO.BookingDTO;
import com.example.hotelmanager.Domain.BookedRoom;

import java.util.List;
public interface BookingService {
    void cancelBooking(Long bookingId);
    List<BookingDTO> getAllBookingsByRoomId(Long roomId);
    String saveBooking(Long roomId, BookedRoom bookingRequest);
    BookedRoom findByBookingConfirmationCode(String confirmationCode);
    List<BookedRoom> getAllBookings();
    List<BookedRoom> getBookingsByUserEmail(String email);
}
