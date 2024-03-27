package com.example.hotelmanager.service;

import com.example.hotelmanager.Domain.Room;
import com.example.hotelmanager.response.RoomResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws SQLException, IOException;
    List<String> getAllRoomTypes();
    List<RoomResponse> getAllRooms() throws SQLException;
    void deleteRoom(Long roomId);
    RoomResponse updateRoom(Long roomId, String roomType, BigDecimal roomPrice, MultipartFile photo) throws IOException, SQLException;
    RoomResponse getRoomById(Long roomId);
    List<RoomResponse> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType) throws SQLException;

}
