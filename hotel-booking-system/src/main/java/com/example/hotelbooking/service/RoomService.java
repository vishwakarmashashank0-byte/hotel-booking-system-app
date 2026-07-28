package com.example.hotelbooking.service;

import com.example.hotelbooking.model.Room;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    Room addRoom(Room room);
    List<Room> getAllRooms();
    Room getRoomById(Long id);
    Room updateRoom(Long id, Room updatedRoom);
    void deleteRoom(Long id);
    List<Room> getAvailableRooms(LocalDate checkIn, LocalDate checkOut);
}
