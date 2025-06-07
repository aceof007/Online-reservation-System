// RoomService.java (Interface)
package com.ORS.Online_reservation_System.services;

import com.ORS.Online_reservation_System.model.Room;
import com.ORS.Online_reservation_System.model.RoomType;
import com.ORS.Online_reservation_System.model.Amenity;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RoomService {

    // Room CRUD operations
    Room createRoom(Room room);
    Room updateRoom(Long roomId, Room room);
    void deleteRoom(Long roomId);
    Optional<Room> getRoomById(Long roomId);
    List<Room> getAllRooms();

    // Room queries
    List<Room> getRoomsByHotel(Long hotelId);
    List<Room> getAvailableRoomsByHotel(Long hotelId);

    @Transactional(readOnly = true)
    List<Room> getRoomsByType(RoomType roomType);

    List<Room> getRoomsByCapacity(Integer minCapacity);
    List<Room> getRoomsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    Optional<Room> getRoomByHotelAndRoomNumber(Long hotelId, String roomNumber);

    // Room availability
    void setRoomAvailability(Long roomId, Boolean isAvailable);
    Long getAvailableRoomCount(Long hotelId);

    // Room amenities
    void addAmenityToRoom(Long roomId, Long amenityId);
    void removeAmenityFromRoom(Long roomId, Long amenityId);
    List<Amenity> getRoomAmenities(Long roomId);

    // Room images
    void addImageToRoom(Long roomId, String imageUrl, String altText, Boolean isPrimary);
    void removeImageFromRoom(Long roomImageId);
    void setPrimaryImage(Long roomId, Long roomImageId);


}