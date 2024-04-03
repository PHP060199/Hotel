package com.example.hotelmanager.mapstruct;
import com.example.hotelmanager.DTO.RoomDTO;
import com.example.hotelmanager.Domain.Room;
import org.apache.tomcat.util.codec.binary.Base64;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.sql.Blob;
import java.sql.SQLException;


@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);
    @Mapping(target = "photo", expression = "java(blobToString(entity.getPhoto()))")
    RoomDTO toDto(Room entity);

    default String blobToString(Blob blob) {
        try {
            byte[] bytes = blob.getBytes(1, (int) blob.length());
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            return null;
        }
    }

}
