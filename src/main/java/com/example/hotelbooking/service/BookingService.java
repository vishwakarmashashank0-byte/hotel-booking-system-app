package com.example.hotelbooking.service;

import com.example.hotelbooking.dto.BookingRequest;
import com.example.hotelbooking.model.Booking;

import java.util.List;

public interface BookingService {
    Booking createBooking(BookingRequest request);
    List<Booking> getAllBookings();
    Booking getBookingById(Long id);
    Booking cancelBooking(Long id);
    Booking checkIn(Long id);
    Booking checkOut(Long id);
    void deleteBooking(Long id);
}
