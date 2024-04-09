package com.example.hotelmanager.controller;

import com.example.hotelmanager.DTO.BookingDTO;
import com.example.hotelmanager.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/all-bookings")
    public ResponseEntity<?> getAllBookings(){
        List<BookingDTO> bookingDTOList = bookingService.getAllBookings();
        return ResponseEntity.ok(bookingDTOList);
    }

    @GetMapping("/all-bookings/{roomId}")
    public ResponseEntity<?> getAllBookingsByRoomId(@PathVariable Long roomId){
        List<BookingDTO> bookingDTOList = bookingService.getAllBookingsByRoomId(roomId);
        return ResponseEntity.ok(bookingDTOList);
    }

    @PostMapping("/room/{roomId}/booking")
    public ResponseEntity<?> saveBooking(@PathVariable Long roomId,
                                         @RequestBody BookingDTO bookingRequest){
        BookingDTO bookingDTO = bookingService.saveBooking(roomId, bookingRequest);
        return ResponseEntity.ok(bookingDTO);
    }

    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode){
        BookingDTO bookingDTO = bookingService.findByBookingConfirmationCode(confirmationCode);
        return ResponseEntity.ok(bookingDTO);
    }

    @GetMapping("/user/{email}/bookings")
    public ResponseEntity<?> getBookingsByUserEmail(@PathVariable String email) {
        List<BookingDTO> bookingDTOList = bookingService.getBookingsByUserEmail(email);
        return ResponseEntity.ok(bookingDTOList);
    }

    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBooking(@PathVariable Long bookingId){
        bookingService.cancelBooking(bookingId);
    }

}
