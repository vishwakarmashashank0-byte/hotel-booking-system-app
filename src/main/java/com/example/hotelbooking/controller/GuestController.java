package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.Guest;
import com.example.hotelbooking.service.GuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Guest addGuest(@Valid @RequestBody Guest guest) {
        return guestService.addGuest(guest);
    }

    @GetMapping
    public List<Guest> getAllGuests() {
        return guestService.getAllGuests();
    }

    @GetMapping("/{id}")
    public Guest getGuestById(@PathVariable Long id) {
        return guestService.getGuestById(id);
    }

    @PutMapping("/{id}")
    public Guest updateGuest(@PathVariable Long id, @Valid @RequestBody Guest guest) {
        return guestService.updateGuest(id, guest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGuest(@PathVariable Long id) {
        guestService.deleteGuest(id);
    }
}
