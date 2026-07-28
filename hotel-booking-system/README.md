# 🏨 Hotel Room Booking System — REST API

A backend project built with **Spring Boot + Spring Data JPA + MySQL** that manages hotel rooms, guests, and bookings — including real availability checking (no double-booking a room for overlapping dates).

## ✨ Features

- **Room management** — add/update/delete rooms, room types (SINGLE, DOUBLE, DELUXE, SUITE)
- **Guest management** — CRUD for guest records
- **Booking engine**
  - Checks real date-overlap availability before confirming a booking (a room can't be double-booked)
  - Auto-calculates total price = nights × price per night
  - Booking lifecycle: `CONFIRMED → CHECKED_IN → CHECKED_OUT`, or `CANCELLED`
- **Validation** on all inputs (`@Valid`) with clean JSON error responses
- **Global exception handling** — consistent error format instead of raw stack traces
- **Layered architecture** — Controller → Service → Repository (industry-standard structure)

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3.2.5
- Spring Data JPA (Hibernate)
- MySQL
- Maven
- Lombok

## 📁 Project Structure

```
src/main/java/com/example/hotelbooking/
├── HotelBookingApplication.java
├── model/          → Room, Guest, Booking entities + enums
├── repository/      → JPA repositories (with custom availability queries)
├── service/         → business logic interfaces
├── service/impl/     → business logic implementations
├── controller/       → REST endpoints
├── dto/             → request/response objects
└── exception/        → custom exceptions + global handler
```

## 🚀 Getting Started

### 1. Prerequisites
- Java 17+ installed
- MySQL installed and running
- Maven (or use your IDE's built-in Maven)

### 2. Configure the database
Open `src/main/resources/application.properties` and set your MySQL username/password:
```properties
spring.datasource.username=root
spring.datasource.password=your_mysql_password
```
The database `hotel_booking_db` will be created automatically on first run.

### 3. Run the project
**Using an IDE (IntelliJ / Eclipse / VS Code):**
Open the folder as a Maven project, let it download dependencies, then run `HotelBookingApplication.java`.

**Using terminal:**
```bash
mvn spring-boot:run
```

The API will start on `http://localhost:8080`.

## 📡 API Endpoints

### Rooms
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/rooms` | Add a new room |
| GET | `/api/rooms` | Get all rooms |
| GET | `/api/rooms/{id}` | Get a room by id |
| GET | `/api/rooms/available?checkIn=2026-08-01&checkOut=2026-08-05` | Get rooms available for a date range |
| PUT | `/api/rooms/{id}` | Update a room |
| DELETE | `/api/rooms/{id}` | Delete a room |

**Sample request body (POST /api/rooms):**
```json
{
  "roomNumber": "101",
  "roomType": "DELUXE",
  "pricePerNight": 3500.00,
  "capacity": 2,
  "available": true
}
```

### Guests
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/guests` | Add a new guest |
| GET | `/api/guests` | Get all guests |
| GET | `/api/guests/{id}` | Get a guest by id |
| PUT | `/api/guests/{id}` | Update a guest |
| DELETE | `/api/guests/{id}` | Delete a guest |

**Sample request body (POST /api/guests):**
```json
{
  "name": "Anuj Vishwakarma",
  "email": "anuj@example.com",
  "phone": "9876543210"
}
```

### Bookings
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/bookings` | Create a booking (checks availability automatically) |
| GET | `/api/bookings` | Get all bookings |
| GET | `/api/bookings/{id}` | Get a booking by id |
| PUT | `/api/bookings/{id}/checkin` | Mark a booking as checked in |
| PUT | `/api/bookings/{id}/checkout` | Mark a booking as checked out |
| PUT | `/api/bookings/{id}/cancel` | Cancel a booking |
| DELETE | `/api/bookings/{id}` | Delete a booking record |

**Sample request body (POST /api/bookings):**
```json
{
  "roomId": 1,
  "guestId": 1,
  "checkInDate": "2026-08-01",
  "checkOutDate": "2026-08-05",
  "numberOfGuests": 2
}
```
If the room is already booked for overlapping dates, this returns a `409 Conflict` with a clear error message instead of creating a double-booking.

## 🧪 Testing the API

Use **Postman** or **Thunder Client** (VS Code extension) to hit the endpoints above. A typical flow to test:
1. `POST /api/rooms` — add a couple of rooms
2. `POST /api/guests` — add a guest
3. `GET /api/rooms/available?checkIn=...&checkOut=...` — confirm rooms show as available
4. `POST /api/bookings` — book a room
5. `GET /api/rooms/available?checkIn=...&checkOut=...` (same dates) — confirm that room no longer appears
6. `PUT /api/bookings/{id}/checkin` then `/checkout` — walk through the booking lifecycle

## 📄 License

MIT — free to use and modify for learning or portfolio purposes.
