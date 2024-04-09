package com.example.hotelmanager.service.Implement;

import com.example.hotelmanager.DTO.BookingDTO;
import com.example.hotelmanager.Domain.BookedRoom;
import com.example.hotelmanager.Domain.Room;
import com.example.hotelmanager.exception.InvalidBookingRequestException;
import com.example.hotelmanager.exception.NotFoundException;
import com.example.hotelmanager.mapstruct.BookingMapper;
import com.example.hotelmanager.repository.BookingRepository;
import com.example.hotelmanager.repository.RoomRepository;
import com.example.hotelmanager.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final RoomRepository roomRepository;

    @Override
    public void cancelBooking(Long bookingId) {
        Optional<BookedRoom> bookedRoom = bookingRepository.findById(bookingId);
        if (bookedRoom.isEmpty()) {
            throw new NotFoundException("Booking is not found!");
        }
        bookingRepository.deleteById(bookingId);
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
    public BookingDTO saveBooking(Long roomId, BookingDTO bookingRequest) {

        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            throw new NotFoundException("Room is not found!");
        }

        if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())){
            throw new InvalidBookingRequestException("Check-in date must come before check-out date");
        }
        List<BookedRoom> existingBookings = bookingRepository.findByRoomId(roomId);
        boolean roomIsAvailable = roomIsAvailable(bookingRequest,existingBookings);
        if (!roomIsAvailable) {
            throw new InvalidBookingRequestException("Sorry, This room is not available for the selected dates;");
        }
        BookedRoom bookedRoom = new BookedRoom();
        bookedRoom.setCheckInDate(bookingRequest.getCheckInDate());
        bookedRoom.setCheckOutDate(bookingRequest.getCheckOutDate());
        bookedRoom.setGuestFullName(bookingRequest.getGuestFullName());
        bookedRoom.setGuestEmail(bookingRequest.getGuestEmail());
        bookedRoom.setNumOfAdults(bookingRequest.getNumOfAdults());
        bookedRoom.setNumOfChildren(bookingRequest.getNumOfChildren());
        bookedRoom.setTotalNumOfGuest(bookedRoom.getNumOfAdults() + bookingRequest.getNumOfChildren());
        bookedRoom.setBookingConfirmationCode(RandomStringUtils.randomNumeric(10));

        room.get().setIsBooked(Boolean.TRUE);
        roomRepository.save(room.get());

        bookedRoom.setRoom(room.get());
        bookingRepository.save(bookedRoom);
        return bookingMapper.toDto(bookedRoom);
    }

    @Override
    public BookingDTO findByBookingConfirmationCode(String confirmationCode) {
        Optional<BookedRoom> bookedRoom = bookingRepository.findByBookingConfirmationCode(confirmationCode);
        if (bookedRoom.isEmpty()) {
            throw new NotFoundException("No booking found with confirmationCode: "+ confirmationCode);
        }
        return bookingMapper.toDto(bookedRoom.get());

    }

    @Override
    public List<BookingDTO> getAllBookings() {
        List<BookedRoom> bookedRoomList = bookingRepository.findAll();
        return bookedRoomList
                .stream()
                .map(bookingMapper::toDto)
                .toList();
    }
    @Override
    public List<BookingDTO> getBookingsByUserEmail(String email) {
        List<BookedRoom> bookedRoomList = bookingRepository.findByGuestEmail(email);
        return bookedRoomList
                .stream()
                .map(bookingMapper::toDto)
                .toList();
    }

    private boolean roomIsAvailable(BookingDTO bookingRequest, List<BookedRoom> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        !bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckInDate()) &&
                                !bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckOutDate())
                );
    }
}
