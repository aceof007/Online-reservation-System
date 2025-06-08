// HotelService.java (Interface)
package com.ORS.Online_reservation_System.services;

import com.ORS.Online_reservation_System.model.Amenity;
import com.ORS.Online_reservation_System.model.Hotel;
import com.ORS.Online_reservation_System.model.HotelImage;
import com.ORS.Online_reservation_System.model.RoomType;

import java.util.List;
import java.util.Optional;

public interface HotelService {

    //implemented
    List<Hotel> findAllHotels();

    // Basic CRUD operations
    Hotel saveHotel(Hotel hotel);
    Optional<Hotel> findHotelById(Long id);
    List<Hotel> findAllActiveHotels();
    Hotel updateHotel(Long id, Hotel hotel);
    void deleteHotel(Long id);
    void deactivateHotel(Long id);
    void activateHotel(Long id);

    // Search operations
    List<Hotel> findHotelsByCity(String city);
    List<Hotel> findHotelsByState(String state);
    List<Hotel> findHotelsByStarRating(Integer starRating);
    List<Hotel> findHotelsByMinStarRating(Integer minStarRating);
    List<Hotel> searchHotelsByName(String name);
    List<Hotel> findHotelsWithAmenity(String amenityName);

    // Amenity operations
    void addAmenityToHotel(Long hotelId, String amenityName);
    void removeAmenityFromHotel(Long hotelId, String amenityName);
    List<Amenity> getHotelAmenities(Long hotelId);
    List<Amenity> getAllUniqueAmenities();

    // Image operations
    HotelImage addImageToHotel(Long hotelId, String imageUrl, String description, Boolean isPrimary);
    void removeImageFromHotel(Long hotelId, Long imageId);
    List<HotelImage> getHotelImages(Long hotelId);
    Optional<HotelImage> getPrimaryImage(Long hotelId);
    void setPrimaryImage(Long hotelId, Long imageId);

    // Validation
    boolean hotelExistsByName(String name);
    boolean isHotelActive(Long hotelId);

    /*List<RoomType> getDistinctRoomTypesForHotel(Long hotelId);*/

}