package com.example.hotelmanager.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private Long bookingId;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate checkInDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate checkOutDate;
    private String guestFullName;
    private String guestEmail;
    private Long numOfAdults;
    private Long numOfChildren;
    private Long totalNumOfGuest;
    private String bookingConfirmationCode;
    private Long roomId;
}
