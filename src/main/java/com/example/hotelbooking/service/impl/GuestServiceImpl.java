package com.example.hotelbooking.service.impl;

import com.example.hotelbooking.exception.ResourceNotFoundException;
import com.example.hotelbooking.model.Guest;
import com.example.hotelbooking.repository.GuestRepository;
import com.example.hotelbooking.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;

    @Override
    public Guest addGuest(Guest guest) {
        return guestRepository.save(guest);
    }

    @Override
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    @Override
    public Guest getGuestById(Long id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id: " + id));
    }

    @Override
    public Guest updateGuest(Long id, Guest updatedGuest) {
        Guest guest = getGuestById(id);
        guest.setName(updatedGuest.getName());
        guest.setEmail(updatedGuest.getEmail());
        guest.setPhone(updatedGuest.getPhone());
        return guestRepository.save(guest);
    }

    @Override
    public void deleteGuest(Long id) {
        Guest guest = getGuestById(id);
        guestRepository.delete(guest);
    }
}
