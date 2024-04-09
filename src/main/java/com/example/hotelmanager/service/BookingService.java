package com.example.hotelmanager.service;

import com.example.hotelmanager.DTO.BookingDTO;

import java.util.List;
public interface BookingService {
    void cancelBooking(Long bookingId);
    List<BookingDTO> getAllBookingsByRoomId(Long roomId);
    BookingDTO saveBooking(Long roomId, BookingDTO bookingRequest);
    BookingDTO findByBookingConfirmationCode(String confirmationCode);

    List<BookingDTO> getAllBookings();

    List<BookingDTO> getBookingsByUserEmail(String email);
}
