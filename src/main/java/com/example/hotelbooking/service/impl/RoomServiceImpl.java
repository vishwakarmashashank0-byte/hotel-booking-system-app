package com.example.hotelbooking.service.impl;

import com.example.hotelbooking.exception.ResourceNotFoundException;
import com.example.hotelbooking.model.Room;
import com.example.hotelbooking.repository.RoomRepository;
import com.example.hotelbooking.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
    }

    @Override
    public Room updateRoom(Long id, Room updatedRoom) {
        Room room = getRoomById(id);
        room.setRoomNumber(updatedRoom.getRoomNumber());
        room.setRoomType(updatedRoom.getRoomType());
        room.setPricePerNight(updatedRoom.getPricePerNight());
        room.setCapacity(updatedRoom.getCapacity());
        room.setAvailable(updatedRoom.isAvailable());
        return roomRepository.save(room);
    }

    @Override
    public void deleteRoom(Long id) {
        Room room = getRoomById(id);
        roomRepository.delete(room);
    }

    @Override
    public List<Room> getAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        return roomRepository.findAvailableRooms(checkIn, checkOut);
    }
}
