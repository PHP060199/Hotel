package com.example.hotelmanager.mapstruct;

import com.example.hotelmanager.DTO.BookingDTO;
import com.example.hotelmanager.Domain.BookedRoom;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-03T22:56:56+0700",
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

        bookingDTO.setBookingId( entity.getBookingId() );
        bookingDTO.setCheckInDate( entity.getCheckInDate() );
        bookingDTO.setCheckOutDate( entity.getCheckOutDate() );
        bookingDTO.setGuestFullName( entity.getGuestFullName() );
        bookingDTO.setBookingConfirmationCode( entity.getBookingConfirmationCode() );

        return bookingDTO;
    }
}
