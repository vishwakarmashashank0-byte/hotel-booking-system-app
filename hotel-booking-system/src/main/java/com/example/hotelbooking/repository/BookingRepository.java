package com.example.hotelbooking.repository;

import com.example.hotelbooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByGuestId(Long guestId);

    List<Booking> findByRoomId(Long roomId);

    /**
     * Returns true if the given room already has a CONFIRMED/CHECKED_IN
     * booking that overlaps with the requested date range.
     */
    @Query("""
        SELECT COUNT(b) > 0 FROM Booking b
        WHERE b.room.id = :roomId
        AND b.status IN ('CONFIRMED', 'CHECKED_IN')
        AND b.checkInDate < :checkOutDate
        AND b.checkOutDate > :checkInDate
        """)
    boolean existsOverlappingBooking(@Param("roomId") Long roomId,
                                      @Param("checkInDate") LocalDate checkInDate,
                                      @Param("checkOutDate") LocalDate checkOutDate);
}
