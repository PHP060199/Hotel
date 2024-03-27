package com.example.hotelmanager.service.Implement;

import com.example.hotelmanager.Domain.BookedRoom;
import com.example.hotelmanager.Domain.Room;
import com.example.hotelmanager.exception.InternalServerException;
import com.example.hotelmanager.exception.InvalidBookingRequestException;
import com.example.hotelmanager.exception.NotFoundException;
import com.example.hotelmanager.exception.PhotoRetrievalException;
import com.example.hotelmanager.repository.RoomRepository;
import com.example.hotelmanager.response.BookingResponse;
import com.example.hotelmanager.response.RoomResponse;
import com.example.hotelmanager.service.BookingService;
import com.example.hotelmanager.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final BookingService bookingService;
    @Override
    public Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws SQLException, IOException {
        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        if (!photo.isEmpty()){
            byte[] photoBytes = photo.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setPhoto(photoBlob);
        }
        return roomRepository.save(room);
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public List<RoomResponse> getAllRooms() throws SQLException {
        List<Room> roomList = roomRepository.findAll();
        List<RoomResponse> roomResponseList = new ArrayList<>();
        for (Room room : roomList) {
            byte[] photoBytes = getRoomPhotoByRoomId(room.getId());
            if (photoBytes != null && photoBytes.length > 0) {
                String base64Photo = Base64.encodeBase64String(photoBytes);
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(base64Photo);
                roomResponseList.add(roomResponse);
            }
        }
        return roomResponseList;
    }



    @Override
    public void deleteRoom(Long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            throw new NotFoundException(" Room not found!");
        }
        roomRepository.deleteById(roomId);
    }

    @Override
    public RoomResponse updateRoom(Long roomId, String roomType, BigDecimal roomPrice, MultipartFile photo) throws IOException, SQLException {
        Optional<Room> checkRoom = roomRepository.findById(roomId);
        if (checkRoom.isEmpty()) {
            throw new NotFoundException(" Room not found!");
        }
        if (checkRoom.get().isBooked()) {
            throw new InvalidBookingRequestException("Room is booked, not update");
        }

        byte[] photoBytes;
        if (photo != null && !photo.isEmpty()) {
            photoBytes = photo.getBytes();
        } else {
            photoBytes = getRoomPhotoByRoomId(roomId);
        }
        if (roomType != null) checkRoom.get().setRoomType(roomType);
        if (roomPrice != null) checkRoom.get().setRoomPrice(roomPrice);
        if (photoBytes != null && photoBytes.length > 0) {
            try {
                checkRoom.get().setPhoto(new SerialBlob(photoBytes));
            } catch (SQLException ex) {
                throw new InternalServerException("Fail updating room");
            }
        }
        roomRepository.save(checkRoom.get());
        return getRoomResponse(checkRoom.get());
    }

    @Override
    public RoomResponse getRoomById(Long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            throw new NotFoundException("Room not found!");
        }
        return getRoomResponse(room.get());
    }

    @Override
    public List<RoomResponse> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType) throws SQLException {
        List<Room> roomList = roomRepository.findAvailableRoomsByDatesAndType(checkInDate, checkOutDate, roomType);
        List<RoomResponse> roomResponseList = new ArrayList<>();
        for (Room room : roomList) {
            byte[] photoBytes = getRoomPhotoByRoomId(room.getId());
            if (photoBytes != null && photoBytes.length > 0) {
                String photoBase64 = Base64.encodeBase64String(photoBytes);
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(photoBase64);
                roomResponseList.add(roomResponse);
            }
        }
        return roomResponseList;
    }

    private RoomResponse getRoomResponse(Room room) {
        List<BookedRoom> bookings =  bookingService.getAllBookingsByRoomId(room.getId());
        List<BookingResponse> bookingInfo = bookings
                .stream()
                .map(booking -> new BookingResponse(booking.getBookingId(),
                        booking.getCheckInDate(),
                        booking.getCheckOutDate(), booking.getBookingConfirmationCode())).toList();
        byte[] photoBytes = null;
        Blob photoBlob = room.getPhoto();
        if (photoBlob != null) {
            try {
                photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
            } catch (SQLException e) {
                throw new PhotoRetrievalException("Error retrieving photo");
            }
        }
        return new RoomResponse(room.getId(),
                room.getRoomType(), room.getRoomPrice(),
                room.isBooked(), photoBytes, bookingInfo);
    }

    private byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            throw new NotFoundException("Room not found!");
        }
        Blob photoBlog = room.get().getPhoto();
        if (photoBlog != null) {
            return photoBlog.getBytes(1, (int) photoBlog.length());
        }
        return null;
    }
}
