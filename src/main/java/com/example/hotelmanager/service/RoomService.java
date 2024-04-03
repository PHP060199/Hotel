package com.example.hotelmanager.service;

import com.example.hotelmanager.DTO.RoomDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    RoomDTO addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws SQLException, IOException;
    List<String> getAllRoomTypes();
    List<RoomDTO> getAllRooms() throws SQLException;
    void deleteRoom(Long roomId);
    RoomDTO updateRoom(Long roomId, String roomType, BigDecimal roomPrice, MultipartFile photo) throws IOException, SQLException;
    RoomDTO getRoomById(Long roomId);
    List<RoomDTO> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType) throws SQLException;

}
