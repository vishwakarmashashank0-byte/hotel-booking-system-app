package com.example.hotelbooking.service.impl;

import com.example.hotelbooking.dto.BookingRequest;
import com.example.hotelbooking.exception.ResourceNotFoundException;
import com.example.hotelbooking.exception.RoomNotAvailableException;
import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.model.BookingStatus;
import com.example.hotelbooking.model.Guest;
import com.example.hotelbooking.model.Room;
import com.example.hotelbooking.repository.BookingRepository;
import com.example.hotelbooking.repository.GuestRepository;
import com.example.hotelbooking.repository.RoomRepository;
import com.example.hotelbooking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;

    @Override
    public Booking createBooking(BookingRequest request) {
        if (!request.getCheckOutDate().isAfter(request.getCheckInDate())) {
            throw new IllegalArgumentException("checkOutDate must be after checkInDate");
        }

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + request.getRoomId()));

        Guest guest = guestRepository.findById(request.getGuestId())
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id: " + request.getGuestId()));

        if (!room.isAvailable()) {
            throw new RoomNotAvailableException("Room " + room.getRoomNumber() + " is currently marked unavailable");
        }

        boolean overlapping = bookingRepository.existsOverlappingBooking(
                room.getId(), request.getCheckInDate(), request.getCheckOutDate());
        if (overlapping) {
            throw new RoomNotAvailableException(
                    "Room " + room.getRoomNumber() + " is already booked for the selected dates");
        }

        long nights = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());
        BigDecimal totalAmount = room.getPricePerNight().multiply(BigDecimal.valueOf(nights));

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setGuest(guest);
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setNumberOfGuests(request.getNumberOfGuests());
        booking.setTotalAmount(totalAmount);
        booking.setStatus(BookingStatus.CONFIRMED);

        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    @Override
    public Booking cancelBooking(Long id) {
        Booking booking = getBookingById(id);
        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking checkIn(Long id) {
        Booking booking = getBookingById(id);
        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Only CONFIRMED bookings can be checked in");
        }
        booking.setStatus(BookingStatus.CHECKED_IN);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking checkOut(Long id) {
        Booking booking = getBookingById(id);
        if (booking.getStatus() != BookingStatus.CHECKED_IN) {
            throw new IllegalStateException("Only CHECKED_IN bookings can be checked out");
        }
        booking.setStatus(BookingStatus.CHECKED_OUT);
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        Booking booking = getBookingById(id);
        bookingRepository.delete(booking);
    }
}
