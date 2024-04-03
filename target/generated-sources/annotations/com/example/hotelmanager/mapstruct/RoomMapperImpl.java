package com.example.hotelmanager.mapstruct;

import com.example.hotelmanager.DTO.RoomDTO;
import com.example.hotelmanager.Domain.Room;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-03T22:56:56+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class RoomMapperImpl implements RoomMapper {

    @Override
    public RoomDTO toDto(Room entity) {
        if ( entity == null ) {
            return null;
        }

        RoomDTO roomDTO = new RoomDTO();

        roomDTO.setId( entity.getId() );
        roomDTO.setRoomType( entity.getRoomType() );
        roomDTO.setRoomPrice( entity.getRoomPrice() );
        roomDTO.setIsBooked( entity.getIsBooked() );

        roomDTO.setPhoto( blobToString(entity.getPhoto()) );

        return roomDTO;
    }
}
