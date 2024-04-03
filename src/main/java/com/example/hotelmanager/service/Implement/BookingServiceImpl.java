package com.example.hotelmanager.service.Implement;

import com.example.hotelmanager.DTO.BookingDTO;
import com.example.hotelmanager.DTO.RoomDTO;
import com.example.hotelmanager.Domain.BookedRoom;
import com.example.hotelmanager.Domain.Room;
import com.example.hotelmanager.mapstruct.BookingMapper;
import com.example.hotelmanager.mapstruct.RoomMapper;
import com.example.hotelmanager.repository.BookingRepository;
import com.example.hotelmanager.service.BookingService;
import com.example.hotelmanager.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Override
    public void cancelBooking(Long bookingId) {

    }

    @Override
    public List<BookingDTO> getAllBookingsByRoomId(Long roomId) {
        List<BookedRoom> bookedRoomList =  bookingRepository.findByRoomId(roomId);
        return bookedRoomList
                .stream()
                .map(bookingMapper::toDto)
                .toList();
    }

    @Override
    public String saveBooking(Long roomId, BookedRoom bookingRequest) {
        return null;
    }

    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
        return null;
    }

    @Override
    public List<BookedRoom> getAllBookings() {
        return null;
    }

    @Override
    public List<BookedRoom> getBookingsByUserEmail(String email) {
        return null;
    }
}
