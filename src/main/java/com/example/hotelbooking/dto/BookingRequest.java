package com.example.hotelbooking.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

/**
 * What the client sends when creating a booking.
 * Keeps the request payload simple (just IDs and dates)
 * instead of exposing the full Room/Guest entities.
 */
@Data
public class BookingRequest {

    @NotNull(message = "roomId is required")
    private Long roomId;

    @NotNull(message = "guestId is required")
    private Long guestId;

    @NotNull(message = "checkInDate is required")
    @FutureOrPresent(message = "checkInDate cannot be in the past")
    private LocalDate checkInDate;

    @NotNull(message = "checkOutDate is required")
    private LocalDate checkOutDate;

    @Positive(message = "numberOfGuests must be greater than zero")
    private int numberOfGuests;
}
