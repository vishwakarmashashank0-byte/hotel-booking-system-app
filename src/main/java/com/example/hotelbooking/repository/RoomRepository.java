package com.example.hotelbooking.repository;

import com.example.hotelbooking.model.Room;
import com.example.hotelbooking.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByRoomNumber(String roomNumber);

    List<Room> findByRoomType(RoomType roomType);

    List<Room> findByIsAvailableTrue();

    /**
     * Finds rooms that are marked available AND have no overlapping
     * CONFIRMED/CHECKED_IN booking for the given date range.
     */
    @Query("""
        SELECT r FROM Room r
        WHERE r.isAvailable = true
        AND r.id NOT IN (
            SELECT b.room.id FROM Booking b
            WHERE b.status IN ('CONFIRMED', 'CHECKED_IN')
            AND b.checkInDate < :checkOutDate
            AND b.checkOutDate > :checkInDate
        )
        """)
    List<Room> findAvailableRooms(@Param("checkInDate") LocalDate checkInDate,
                                   @Param("checkOutDate") LocalDate checkOutDate);
}
