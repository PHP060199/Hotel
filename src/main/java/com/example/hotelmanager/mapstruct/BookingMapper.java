package com.example.hotelmanager.mapstruct;

import com.example.hotelmanager.DTO.BookingDTO;
import com.example.hotelmanager.DTO.RoomDTO;
import com.example.hotelmanager.Domain.BookedRoom;
import com.example.hotelmanager.Domain.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mapping(source = "room.id", target = "roomId")
    BookingDTO toDto(BookedRoom entity);
}
