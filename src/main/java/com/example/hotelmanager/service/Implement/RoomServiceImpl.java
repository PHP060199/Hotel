package com.example.hotelmanager.service.Implement;

import com.example.hotelmanager.DTO.RoomDTO;
import com.example.hotelmanager.Domain.Room;
import com.example.hotelmanager.exception.InternalServerException;
import com.example.hotelmanager.exception.InvalidBookingRequestException;
import com.example.hotelmanager.exception.NotFoundException;
import com.example.hotelmanager.mapstruct.RoomMapper;
import com.example.hotelmanager.repository.RoomRepository;
import com.example.hotelmanager.service.BookingService;
import com.example.hotelmanager.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final BookingService bookingService;
    private final RoomMapper roomMapper;
    @Override
    public RoomDTO addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws SQLException, IOException {
        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        if (!photo.isEmpty()){
            byte[] photoBytes = photo.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setPhoto(photoBlob);
        }
        roomRepository.save(room);
        return roomMapper.toDto(room);
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public List<RoomDTO> getAllRooms() {
        List<Room> roomList = roomRepository.findAll();
        List<RoomDTO> roomDTOList = roomList
                .stream()
                .map(roomMapper::toDto)
                .toList();
        roomDTOList.forEach(roomDTO -> roomDTO.setBookingDTOList(bookingService.getAllBookingsByRoomId(roomDTO.getId())));
        return roomDTOList;
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
    public RoomDTO updateRoom(Long roomId, String roomType, BigDecimal roomPrice, MultipartFile photo) throws IOException, SQLException {
        Optional<Room> checkRoom = roomRepository.findById(roomId);
        if (checkRoom.isEmpty()) {
            throw new NotFoundException(" Room not found!");
        }
        if (checkRoom.get().getIsBooked()) {
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
        return roomMapper.toDto(checkRoom.get());
    }

    @Override
    public RoomDTO getRoomById(Long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            throw new NotFoundException("Room not found!");
        }
        return roomMapper.toDto(room.get());
    }

    @Override
    public List<RoomDTO> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType) throws SQLException {
        List<Room> roomList = roomRepository.findAvailableRoomsByDatesAndType(checkInDate, checkOutDate, roomType);
        List<RoomDTO> roomDTOList = roomList
                .stream()
                .map(roomMapper::toDto)
                .toList();
        roomDTOList.forEach(roomDTO -> roomDTO.setBookingDTOList(bookingService.getAllBookingsByRoomId(roomDTO.getId())));
        return roomDTOList;
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
