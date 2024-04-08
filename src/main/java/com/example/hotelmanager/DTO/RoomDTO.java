package com.example.hotelmanager.DTO;

import lombok.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class RoomDTO {
    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private Boolean isBooked;
    private String photo;
    private List<BookingDTO> bookingDTOList = new ArrayList<>();
}
