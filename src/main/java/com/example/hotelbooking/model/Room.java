package com.example.hotelbooking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Room number is required")
    @Column(unique = true, nullable = false)
    private String roomNumber;

    @NotNull(message = "Room type is required")
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @NotNull(message = "Price per night is required")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal pricePerNight;

    @Positive(message = "Capacity must be greater than zero")
    private int capacity;

    /** General availability flag — false when a room is under maintenance etc. */
    private boolean isAvailable = true;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Booking> bookings;
}
