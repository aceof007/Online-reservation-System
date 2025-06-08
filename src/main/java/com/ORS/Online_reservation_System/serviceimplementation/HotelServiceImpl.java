// HotelServiceImpl.java
package com.ORS.Online_reservation_System.serviceimplementation;

import com.ORS.Online_reservation_System.model.*;
import com.ORS.Online_reservation_System.repositories.AmenityRepository;
import com.ORS.Online_reservation_System.repositories.HotelRepository;
import com.ORS.Online_reservation_System.repositories.HotelImageRepository;
import com.ORS.Online_reservation_System.repositories.RoomRepository;
import com.ORS.Online_reservation_System.services.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelImageRepository hotelImageRepository;
    private final AmenityRepository amenityRepository;
    private final RoomRepository roomRepository;

    @Override
    public Hotel saveHotel(Hotel hotel) {
        // Set default values if not provided
        if (hotel.getCheckInTime() == null) {
            hotel.setCheckInTime(LocalTime.of(15, 0)); // 3:00 PM
        }
        if (hotel.getCheckOutTime() == null) {
            hotel.setCheckOutTime(LocalTime.of(11, 0)); // 11:00 AM
        }
        if (hotel.getIsActive() == null) {
            hotel.setIsActive(true);
        }

        return hotelRepository.save(hotel);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Hotel> findHotelById(Long id) {
        return hotelRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Hotel> findAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Hotel> findAllActiveHotels() {
        return hotelRepository.findByIsActiveTrue();
    }

    @Override
    public Hotel updateHotel(Long id, Hotel hotel) {
        return hotelRepository.findById(id)
                .map(existingHotel -> {
                    existingHotel.setName(hotel.getName());
                    existingHotel.setAddress(hotel.getAddress());
                    existingHotel.setCity(hotel.getCity());
                    existingHotel.setState(hotel.getState());
                    existingHotel.setCountry(hotel.getCountry());
                    existingHotel.setPostalCode(hotel.getPostalCode());
                    existingHotel.setPhoneNumber(hotel.getPhoneNumber());
                    existingHotel.setEmail(hotel.getEmail());
                    existingHotel.setWebsite(hotel.getWebsite());
                    existingHotel.setDescription(hotel.getDescription());
                    existingHotel.setStarRating(hotel.getStarRating());
                    existingHotel.setContactInfo(hotel.getContactInfo());
                    existingHotel.setCheckInTime(hotel.getCheckInTime());
                    existingHotel.setCheckOutTime(hotel.getCheckOutTime());
                    existingHotel.setLatitude(hotel.getLatitude());
                    existingHotel.setLongitude(hotel.getLongitude());
                    existingHotel.setManagedBy(hotel.getManagedBy());

                    return hotelRepository.save(existingHotel);
                })
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
    }

    @Override
    public void deleteHotel(Long id) {
        if (hotelRepository.existsById(id)) {
            hotelRepository.deleteById(id);
        } else {
            throw new RuntimeException("Hotel not found with id: " + id);
        }
    }

    @Override
    public void deactivateHotel(Long id) {
        hotelRepository.findById(id)
                .ifPresentOrElse(
                        hotel -> {
                            hotel.setIsActive(false);
                            hotelRepository.save(hotel);
                        },
                        () -> {
                            throw new RuntimeException("Hotel not found with id: " + id);
                        }
                );
    }

    @Override
    public void activateHotel(Long id) {
        hotelRepository.findById(id)
                .ifPresentOrElse(
                        hotel -> {
                            hotel.setIsActive(true);
                            hotelRepository.save(hotel);
                        },
                        () -> {
                            throw new RuntimeException("Hotel not found with id: " + id);
                        }
                );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Hotel> findHotelsByCity(String city) {
        return hotelRepository.findByCityIgnoreCaseAndIsActiveTrue(city);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Hotel> findHotelsByState(String state) {
        return hotelRepository.findByStateIgnoreCaseAndIsActiveTrue(state);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Hotel> findHotelsByStarRating(Integer starRating) {
        return hotelRepository.findByStarRatingAndIsActiveTrue(starRating);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Hotel> findHotelsByMinStarRating(Integer minStarRating) {
        return hotelRepository.findByStarRatingGreaterThanEqualAndIsActiveTrue(minStarRating);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Hotel> searchHotelsByName(String name) {
        return hotelRepository.findByNameContainingIgnoreCaseAndIsActiveTrue(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Hotel> findHotelsWithAmenity(String amenityName) {
        return hotelRepository.findHotelsWithAmenity(amenityName);
    }

    @Override
    public void addAmenityToHotel(Long hotelId, String amenityName) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + hotelId));

        Amenity amenity = amenityRepository.findByNameIgnoreCase(amenityName)
                .orElseThrow(() -> new RuntimeException("Amenity not found: " + amenityName));

        if (hotel.getAmenities().contains(amenity)) {
            throw new RuntimeException("Amenity already exists for this hotel");
        }

        hotel.getAmenities().add(amenity);
        hotelRepository.save(hotel);
    }


    @Override
    public void removeAmenityFromHotel(Long hotelId, String amenityName) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + hotelId));

        Amenity amenity = amenityRepository.findByNameIgnoreCase(amenityName)
                .orElseThrow(() -> new RuntimeException("Amenity not found: " + amenityName));

        if (!hotel.getAmenities().remove(amenity)) {
            throw new RuntimeException("Amenity not associated with this hotel");
        }

        hotelRepository.save(hotel);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Amenity> getHotelAmenities(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + hotelId));
        return hotel.getAmenities();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Amenity> getAllUniqueAmenities() {
        return amenityRepository.findAll();
    }


    @Override
    public HotelImage addImageToHotel(Long hotelId, String imageUrl, String description, Boolean isPrimary) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + hotelId));

        // If this is set as primary, remove primary flag from other images
        if (isPrimary != null && isPrimary) {
            List<HotelImage> existingImages = hotelImageRepository.findByHotel_HotelId(hotelId);
            existingImages.forEach(img -> {
                img.setIsPrimary(false);
                hotelImageRepository.save(img);
            });
        }

        HotelImage hotelImage = HotelImage.builder()
                .hotel(hotel)
                .imageUrl(imageUrl)
                .build();

        return hotelImageRepository.save(hotelImage);
    }

    @Override
    public void removeImageFromHotel(Long hotelId, Long imageId) {
        HotelImage image = hotelImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + imageId));

        if (!image.getHotel().getHotelId().equals(hotelId)) {
            throw new RuntimeException("Image does not belong to the specified hotel");
        }

        hotelImageRepository.delete(image);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelImage> getHotelImages(Long hotelId) {
        return hotelImageRepository.findByHotel_HotelId(hotelId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HotelImage> getPrimaryImage(Long hotelId) {
        return hotelImageRepository.findByHotel_HotelIdAndIsPrimaryTrue(hotelId);
    }

    @Override
    public void setPrimaryImage(Long hotelId, Long imageId) {
        // First, remove primary flag from all images of this hotel
        List<HotelImage> existingImages = hotelImageRepository.findByHotel_HotelId(hotelId);
        existingImages.forEach(img -> {
            img.setIsPrimary(false);
            hotelImageRepository.save(img);
        });

        // Then set the specified image as primary
        HotelImage image = hotelImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + imageId));

        if (!image.getHotel().getHotelId().equals(hotelId)) {
            throw new RuntimeException("Image does not belong to the specified hotel");
        }

        image.setIsPrimary(true);
        hotelImageRepository.save(image);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hotelExistsByName(String name) {
        return hotelRepository.findByNameIgnoreCase(name).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isHotelActive(Long hotelId) {
        return hotelRepository.findById(hotelId)
                .map(Hotel::getIsActive)
                .orElse(false);
    }

    /*@Override
    public List<RoomType> getDistinctRoomTypesForHotel(Long hotelId) {
        // Fetch all rooms for this hotel
        List<Room> hotelRooms = roomRepository.findByHotel_HotelId(hotelId);

        // Extract distinct RoomTypes
        return hotelRooms.stream()
                .map(Room::getRoomType)
                .distinct()
                .toList();
    }*/

}