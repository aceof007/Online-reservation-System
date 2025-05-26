package com.ORS.Online_reservation_System.serviceimplementation;

import com.ORS.Online_reservation_System.model.*;
import com.ORS.Online_reservation_System.repositories.*;
import com.ORS.Online_reservation_System.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final RoomAmenityRepository roomAmenityRepository;
    private final RoomImageRepository roomImageRepository;
    private final AmenityRepository amenityRepository;

    @Override
    public Room createRoom(Room room) {
        // Validate hotel exists (you'll need to inject HotelRepository for this)
        // Validate room type exists
        if (!roomTypeRepository.existsById(room.getRoomTypeId())) {
            throw new IllegalArgumentException("Room type not found");
        }

        // Check if room number already exists for this hotel
        if (roomRepository.existsByHotelIdAndRoomNumber(room.getHotelId(), room.getRoomNumber())) {
            throw new IllegalArgumentException("Room number already exists for this hotel");
        }

        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(Long roomId, Room room) {
        Room existingRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        // Check room number uniqueness if changed
        if (!existingRoom.getRoomNumber().equals(room.getRoomNumber())) {
            if (roomRepository.existsByHotelIdAndRoomNumber(room.getHotelId(), room.getRoomNumber())) {
                throw new IllegalArgumentException("Room number already exists for this hotel");
            }
        }

        // Update fields
        existingRoom.setRoomNumber(room.getRoomNumber());
        existingRoom.setRoomTypeId(room.getRoomTypeId());
        existingRoom.setPricePerNight(room.getPricePerNight());
        existingRoom.setCapacity(room.getCapacity());
        existingRoom.setDescription(room.getDescription());
        existingRoom.setIsAvailable(room.getIsAvailable());

        return roomRepository.save(existingRoom);
    }

    @Override
    public void deleteRoom(Long roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new IllegalArgumentException("Room not found");
        }

        // Delete related amenities and images
        roomAmenityRepository.deleteByRoomId(roomId);
        roomImageRepository.deleteByRoomId(roomId);

        roomRepository.deleteById(roomId);
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
        return roomRepository.findByHotelId(hotelId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Room> getAvailableRoomsByHotel(Long hotelId) {
        return roomRepository.findByHotelIdAndIsAvailableTrue(hotelId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Room> getRoomsByType(Long roomTypeId) {
        return roomRepository.findByRoomTypeId(roomTypeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Room> getRoomsByCapacity(Integer minCapacity) {
        return roomRepository.findByCapacityGreaterThanEqual(minCapacity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Room> getRoomsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return roomRepository.findByPricePerNightBetween(minPrice, maxPrice);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Room> getRoomByHotelAndRoomNumber(Long hotelId, String roomNumber) {
        return roomRepository.findByHotelIdAndRoomNumber(hotelId, roomNumber);
    }

    @Override
    public void setRoomAvailability(Long roomId, Boolean isAvailable) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        room.setIsAvailable(isAvailable);
        roomRepository.save(room);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getAvailableRoomCount(Long hotelId) {
        return roomRepository.countByHotelIdAndIsAvailableTrue(hotelId);
    }

    @Override
    public void addAmenityToRoom(Long roomId, Long amenityId) {
        // Validate room and amenity exist
        if (!roomRepository.existsById(roomId)) {
            throw new IllegalArgumentException("Room not found");
        }
        if (!amenityRepository.existsById(amenityId)) {
            throw new IllegalArgumentException("Amenity not found");
        }

        // Check if already exists
        if (roomAmenityRepository.existsByRoomIdAndAmenityId(roomId, amenityId)) {
            throw new IllegalArgumentException("Room already has this amenity");
        }

        RoomAmenity roomAmenity = RoomAmenity.builder()
                .roomId(roomId)
                .amenityId(amenityId)
                .build();

        roomAmenityRepository.save(roomAmenity);
    }

    @Override
    public void removeAmenityFromRoom(Long roomId, Long amenityId) {
        roomAmenityRepository.deleteByRoomIdAndAmenityId(roomId, amenityId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Amenity> getRoomAmenities(Long roomId) {
        return roomAmenityRepository.findByRoomId(roomId)
                .stream()
                .map(RoomAmenity::getAmenity)
                .toList();
    }

    @Override
    public void addImageToRoom(Long roomId, String imageUrl, String altText, Boolean isPrimary) {
        if (!roomRepository.existsById(roomId)) {
            throw new IllegalArgumentException("Room not found");
        }

        // If setting as primary, unset other primary images
        if (isPrimary) {
            roomImageRepository.findByRoomIdAndIsPrimaryTrue(roomId)
                    .ifPresent(primaryImage -> {
                        primaryImage.setIsPrimary(false);
                        roomImageRepository.save(primaryImage);
                    });
        }

        RoomImage roomImage = RoomImage.builder()
                .roomId(roomId)
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
        roomImageRepository.findByRoomIdAndIsPrimaryTrue(roomId)
                .ifPresent(primaryImage -> {
                    primaryImage.setIsPrimary(false);
                    roomImageRepository.save(primaryImage);
                });

        // Set new primary
        RoomImage newPrimary = roomImageRepository.findById(roomImageId)
                .orElseThrow(() -> new IllegalArgumentException("Room image not found"));

        if (!newPrimary.getRoomId().equals(roomId)) {
            throw new IllegalArgumentException("Image does not belong to this room");
        }

        newPrimary.setIsPrimary(true);
        roomImageRepository.save(newPrimary);
    }

    // Room Type methods
    @Override
    @Transactional(readOnly = true)
    public List<RoomType> getAllRoomTypes() {
        return roomTypeRepository.findByIsActiveTrueOrderByTypeName();
    }

    @Override
    public RoomType createRoomType(RoomType roomType) {
        if (roomTypeRepository.existsByTypeName(roomType.getTypeName())) {
            throw new IllegalArgumentException("Room type name already exists");
        }
        return roomTypeRepository.save(roomType);
    }

    @Override
    public RoomType updateRoomType(Long roomTypeId, RoomType roomType) {
        RoomType existing = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Room type not found"));

        // Check name uniqueness if changed
        if (!existing.getTypeName().equals(roomType.getTypeName())) {
            if (roomTypeRepository.existsByTypeName(roomType.getTypeName())) {
                throw new IllegalArgumentException("Room type name already exists");
            }
        }

        existing.setTypeName(roomType.getTypeName());
        existing.setDescription(roomType.getDescription());
        existing.setIsActive(roomType.getIsActive());

        return roomTypeRepository.save(existing);
    }

    @Override
    public void deleteRoomType(Long roomTypeId) {
        // Check if any rooms use this type
        if (!roomRepository.findByRoomTypeId(roomTypeId).isEmpty()) {
            throw new IllegalArgumentException("Cannot delete room type that is in use");
        }

        roomTypeRepository.deleteById(roomTypeId);
    }

    // Amenity methods
    @Override
    @Transactional(readOnly = true)
    public List<Amenity> getAllAmenities() {
        return amenityRepository.findByIsActiveTrueOrderByName();
    }

    @Override
    public Amenity createAmenity(Amenity amenity) {
        if (amenityRepository.existsByName(amenity.getName())) {
            throw new IllegalArgumentException("Amenity name already exists");
        }
        return amenityRepository.save(amenity);
    }

    @Override
    public Amenity updateAmenity(Long amenityId, Amenity amenity) {
        Amenity existing = amenityRepository.findById(amenityId)
                .orElseThrow(() -> new IllegalArgumentException("Amenity not found"));

        // Check name uniqueness if changed
        if (!existing.getName().equals(amenity.getName())) {
            if (amenityRepository.existsByName(amenity.getName())) {
                throw new IllegalArgumentException("Amenity name already exists");
            }
        }

        existing.setName(amenity.getName());
        existing.setDescription(amenity.getDescription());
        existing.setIcon(amenity.getIcon());
        existing.setIsActive(amenity.getIsActive());

        return amenityRepository.save(existing);
    }

    @Override
    public void deleteAmenity(Long amenityId) {
        // Check if any rooms use this amenity
        if (!roomAmenityRepository.findByAmenityId(amenityId).isEmpty()) {
            throw new IllegalArgumentException("Cannot delete amenity that is in use");
        }

        amenityRepository.deleteById(amenityId);
    }
}