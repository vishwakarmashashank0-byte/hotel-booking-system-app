package com.example.hotelbooking.service;

import com.example.hotelbooking.model.Guest;

import java.util.List;

public interface GuestService {
    Guest addGuest(Guest guest);
    List<Guest> getAllGuests();
    Guest getGuestById(Long id);
    Guest updateGuest(Long id, Guest updatedGuest);
    void deleteGuest(Long id);
}
