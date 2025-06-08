package com.ORS.Online_reservation_System.serviceimplementation;

import com.ORS.Online_reservation_System.model.*;
import com.ORS.Online_reservation_System.repositories.*;
import com.ORS.Online_reservation_System.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomImageRepository roomImageRepository;
    private final AmenityRepository amenityRepository;
    private final HotelRepository hotelRepository;

    //Fotsing implemented functions
    public Room findCheapestRooms(Long hotelId) {
        List<Room> allRooms = roomRepository.findByHotel_HotelId(hotelId);

        if (allRooms.isEmpty()) {
            return null; // or throw exception if needed
        }

        Room cheapestRoom = allRooms.get(0); // Start with the first actual room
        BigDecimal minPrice = cheapestRoom.getPricePerNight();

        for (Room room : allRooms) {
            if (room.getPricePerNight().compareTo(minPrice) < 0) {
                minPrice = room.getPricePerNight();
                cheapestRoom = room;
            }
        }

        System.out.println("Cheapest Room: " + cheapestRoom);
        return cheapestRoom;
    }



    @Override
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }
    /*
        // Validate hotel exists (assumes you have hotelRepository injected)
        if (!hotelRepository.existsById(room.getHotel().getHotelId())) {
            throw new IllegalArgumentException("Hotel not found");
        }

        // Validate roomType is non-null (enum, no DB check needed)
        if (room.getRoomType() == null) {
            throw new IllegalArgumentException("Room type is required");
        }

        // Check if room number already exists for this hotel
        if (roomRepository.existsByHotel_HotelIdAndRoomNumber(
                room.getHotel().getHotelId(), room.getRoomNumber())) {
            throw new IllegalArgumentException("Room number already exists for this hotel");
        }

        return roomRepository.save(room);
    }
    */
    @Override
    public Room updateRoom(Long roomId, Room room) {
        return roomRepository.save(room);
    }
    /*
        Room existingRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        // Check room number uniqueness if changed
        if (!existingRoom.getRoomNumber().equals(room.getRoomNumber())) {
            if (roomRepository.existsByHotel_HotelIdAndRoomNumber(
                    existingRoom.getHotel().getHotelId(), room.getRoomNumber())) {
                throw new IllegalArgumentException("Room number already exists for this hotel");
            }
        }

        // Update fields
        existingRoom.setRoomNumber(room.getRoomNumber());
        existingRoom.setRoomType(room.getRoomType());
        existingRoom.setPricePerNight(room.getPricePerNight());
        existingRoom.setCapacity(room.getCapacity());
        existingRoom.setDescription(room.getDescription());
        existingRoom.setIsAvailable(room.getIsAvailable());

        return roomRepository.save(existingRoom);
    }*/

    @Override
    public void deleteRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        // Delete related images (assuming your repository method is correct)
        roomImageRepository.deleteByRoom_RoomId(roomId);

        // Delete room itself
        roomRepository.delete(room);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Room> getRoomById(Long roomId) {
        return roomRepository.findById(roomId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Room> getRoomsByHotel(Long hotelId) {
        return roomRepository.findByHotel_HotelId(hotelId);
    }

    /*@Override
    @Transactional(readOnly = true)
    public List<Room> getAvailableRoomsByHotel(Long hotelId) {
        return roomRepository.findByHotel_HotelIdAndIsAvailableTrue(hotelId);
    }*/

    /*@Transactional(readOnly = true)
    public List<Room> getRoomsByType(RoomType roomType) {
        return roomRepository.findByRoomType(roomType);
    }*/

    /*@Override
    @Transactional(readOnly = true)
    public List<Room> getRoomsByCapacity(Integer minCapacity) {
        return roomRepository.findByCapacityGreaterThanEqual(minCapacity);
    }*/

    @Override
    @Transactional(readOnly = true)
    public List<Room> getRoomsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return roomRepository.findByPricePerNightBetween(minPrice, maxPrice);
    }

    /*@Override
    @Transactional(readOnly = true)
    public Optional<Room> getRoomByHotelAndRoomNumber(Long hotelId, String roomNumber) {
        return roomRepository.findByHotel_HotelIdAndRoomNumber(hotelId, roomNumber);
    }*/

    @Override
    public void setRoomAvailability(Long roomId, Boolean isAvailable) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        room.setIsAvailable(isAvailable);
        roomRepository.save(room);
    }

    @Transactional(readOnly = true)
    public Long getAvailableRoomCount(Long hotelId) {
        return roomRepository.countByHotel_HotelIdAndIsAvailableTrue(hotelId);
    }

    @Override
    public void addAmenityToRoom(Long roomId, Long amenityId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        Amenity amenity = amenityRepository.findById(amenityId)
                .orElseThrow(() -> new IllegalArgumentException("Amenity not found"));

        if (room.getRoomAmenities().contains(amenity)) {
            throw new IllegalArgumentException("Room already has this amenity");
        }

        room.getRoomAmenities().add(amenity);
        roomRepository.save(room);
    }


    @Override
    public void removeAmenityFromRoom(Long roomId, Long amenityId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        Amenity amenity = amenityRepository.findById(amenityId)
                .orElseThrow(() -> new IllegalArgumentException("Amenity not found"));

        if (!room.getRoomAmenities().contains(amenity)) {
            throw new IllegalArgumentException("Room does not have this amenity");
        }

        room.getRoomAmenities().remove(amenity);
        roomRepository.save(room);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Amenity> getRoomAmenities(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        return room.getRoomAmenities();
    }


    @Override
    public void addImageToRoom(Long roomId, String imageUrl, String altText, Boolean isPrimary) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        // If setting as primary, unset any existing primary image
        roomImageRepository.findByRoom_RoomIdAndIsPrimaryTrue(roomId)
                .ifPresent(existingPrimary -> {
                    existingPrimary.setIsPrimary(false);
                    roomImageRepository.save(existingPrimary);
                });

        RoomImage roomImage = RoomImage.builder()
                .room(room)
                .imageUrl(imageUrl)
                .altText(altText)
                .isPrimary(isPrimary)
                .build();

        roomImageRepository.save(roomImage);
    }


    @Override
    public void removeImageFromRoom(Long roomImageId) {
        roomImageRepository.deleteById(roomImageId);
    }

    @Override
    public void setPrimaryImage(Long roomId, Long roomImageId) {
        // Unset current primary
        roomImageRepository.findByRoom_RoomIdAndIsPrimaryTrue(roomId)
                .ifPresent(primaryImage -> {
                    primaryImage.setIsPrimary(false);
                    roomImageRepository.save(primaryImage);
                });

        // Set new primary
        RoomImage newPrimary = roomImageRepository.findById(roomImageId)
                .orElseThrow(() -> new IllegalArgumentException("Room image not found"));

        if (!newPrimary.getRoom().getRoomId().equals(roomId)) {
            throw new IllegalArgumentException("Image does not belong to this room");
        }

        newPrimary.setIsPrimary(true);
        roomImageRepository.save(newPrimary);
    }

}