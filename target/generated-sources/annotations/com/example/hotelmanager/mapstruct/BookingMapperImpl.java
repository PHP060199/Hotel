package com.example.hotelmanager.mapstruct;

import com.example.hotelmanager.DTO.BookingDTO;
import com.example.hotelmanager.Domain.BookedRoom;
import com.example.hotelmanager.Domain.Room;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-09T04:08:28+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class BookingMapperImpl implements BookingMapper {

    @Override
    public BookingDTO toDto(BookedRoom entity) {
        if ( entity == null ) {
            return null;
        }

        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setRoomId( entityRoomId( entity ) );
        bookingDTO.setBookingId( entity.getBookingId() );
        bookingDTO.setCheckInDate( entity.getCheckInDate() );
        bookingDTO.setCheckOutDate( entity.getCheckOutDate() );
        bookingDTO.setGuestFullName( entity.getGuestFullName() );
        bookingDTO.setGuestEmail( entity.getGuestEmail() );
        bookingDTO.setNumOfAdults( entity.getNumOfAdults() );
        bookingDTO.setNumOfChildren( entity.getNumOfChildren() );
        bookingDTO.setTotalNumOfGuest( entity.getTotalNumOfGuest() );
        bookingDTO.setBookingConfirmationCode( entity.getBookingConfirmationCode() );

        return bookingDTO;
    }

    private Long entityRoomId(BookedRoom bookedRoom) {
        if ( bookedRoom == null ) {
            return null;
        }
        Room room = bookedRoom.getRoom();
        if ( room == null ) {
            return null;
        }
        Long id = room.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
