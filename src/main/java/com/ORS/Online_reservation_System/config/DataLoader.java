// DataLoader.java
package com.ORS.Online_reservation_System.config;

import com.ORS.Online_reservation_System.model.*;
import com.ORS.Online_reservation_System.repositories.AmenityRepository;
import com.ORS.Online_reservation_System.repositories.FAQRepository;
import com.ORS.Online_reservation_System.repositories.HotelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Arrays;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadAmenities(AmenityRepository amenityRepository,
                                           HotelRepository hotelRepository,
                                           FAQRepository faqRepository) {
        return args -> {
            if (amenityRepository.count() == 0) {

                Amenity am1 = Amenity.builder()
                        .name("Free WiFi")
                        .description("High-speed internet")
                        .icon("wifi")
                        .isActive(true)
                        .category(AmenityCategory.GENERAL)
                        .build();

                Amenity am2 = Amenity.builder()
                        .name("Valet parking")
                        .description("On-site valet parking")
                        .icon("car")
                        .isActive(true)
                        .category(AmenityCategory.GENERAL)
                        .build();

                Amenity am3 = Amenity.builder()
                        .name("Indoor pool")
                        .description("Heated indoor swimming pool")
                        .icon("swimming-pool")
                        .isActive(true)
                        .category(AmenityCategory.GENERAL)
                        .build();

                Amenity am4 = Amenity.builder()
                        .name("Spa & wellness center")
                        .description("Spa and wellness treatments")
                        .icon("spa")
                        .isActive(true)
                        .category(AmenityCategory.GENERAL)
                        .build();

                Amenity am5 = Amenity.builder()
                        .name("Fitness center")
                        .description("Modern gym facilities")
                        .icon("dumbbell")
                        .isActive(true)
                        .category(AmenityCategory.GENERAL)
                        .build();

                Amenity am6 = Amenity.builder()
                        .name("Air conditioning")
                        .description("Climate control")
                        .icon("snowflake")
                        .isActive(true)
                        .category(AmenityCategory.IN_YOUR_ROOM)
                        .build();

                Amenity am7 = Amenity.builder()
                        .name("Smart TV")
                        .description("Streaming-enabled television")
                        .icon("tv")
                        .isActive(true)
                        .category(AmenityCategory.IN_YOUR_ROOM)
                        .build();

                Amenity am8 = Amenity.builder()
                        .name("Minibar")
                        .description("In-room minibar")
                        .icon("wine-bottle")
                        .isActive(true)
                        .category(AmenityCategory.IN_YOUR_ROOM)
                        .build();

                Amenity am9 = Amenity.builder()
                        .name("Coffee/tea maker")
                        .description("In-room hot beverages")
                        .icon("coffee")
                        .isActive(true)
                        .category(AmenityCategory.IN_YOUR_ROOM)
                        .build();

                Amenity am10 = Amenity.builder()
                        .name("Bathrobes & slippers")
                        .description("Comfortable amenities")
                        .icon("bath")
                        .isActive(true)
                        .category(AmenityCategory.IN_YOUR_ROOM)
                        .build();

                Amenity am11 = Amenity.builder()
                        .name("3 restaurants")
                        .description("Multiple dining options")
                        .icon("utensils")
                        .isActive(true)
                        .category(AmenityCategory.DINING)
                        .build();

                Amenity am12 = Amenity.builder()
                        .name("Bar/Lounge")
                        .description("Relaxing lounge and drinks")
                        .icon("glass-martini")
                        .isActive(true)
                        .category(AmenityCategory.DINING)
                        .build();

                Amenity am13 = Amenity.builder()
                        .name("Room service (24-hour)")
                        .description("Anytime room service")
                        .icon("concierge-bell")
                        .isActive(true)
                        .category(AmenityCategory.DINING)
                        .build();

                Amenity am14 = Amenity.builder()
                        .name("Breakfast available")
                        .description("Start your day right")
                        .icon("egg")
                        .isActive(true)
                        .category(AmenityCategory.DINING)
                        .build();

                Amenity am15 = Amenity.builder()
                        .name("24-hour front desk")
                        .description("Always available")
                        .icon("clock")
                        .isActive(true)
                        .category(AmenityCategory.SERVICES)
                        .build();

                Amenity am16 = Amenity.builder()
                        .name("Concierge service")
                        .description("Assistance & planning")
                        .icon("user-tie")
                        .isActive(true)
                        .category(AmenityCategory.SERVICES)
                        .build();

                Amenity am17 = Amenity.builder()
                        .name("Luggage storage")
                        .description("Secure luggage holding")
                        .icon("suitcase")
                        .isActive(true)
                        .category(AmenityCategory.SERVICES)
                        .build();

                Amenity am18 = Amenity.builder()
                        .name("Laundry service")
                        .description("Cleaning & ironing")
                        .icon("soap")
                        .isActive(true)
                        .category(AmenityCategory.SERVICES)
                        .build();

                Amenity am19 = Amenity.builder()
                        .name("Business center")
                        .description("Office facilities")
                        .icon("briefcase")
                        .isActive(true)
                        .category(AmenityCategory.SERVICES)
                        .build();

                amenityRepository.saveAll(List.of(
                        am1, am2, am3, am4, am5,
                        am6, am7, am8, am9, am10,
                        am11, am12, am13, am14,
                        am15, am16, am17, am18, am19
                ));

                System.out.println("Amenities loaded.");

                Hotel hotel1 = Hotel.builder()
                        .name("Sunset Paradise Resort")
                        .address("123 Beach Avenue, Miami, FL")
                        .city("Miami")
                        .state("Florida")
                        .country("USA")
                        .postalCode("33101")
                        .phoneNumber("+1-305-555-1234")
                        .email("contact@sunsetparadise.com")
                        .propertyType(PropertyType.RESORT)
                        .website("https://www.sunsetparadise.com")
                        .description("A luxury beachfront resort with stunning sunset views.")
                        .starRating(5)
                        .contactInfo("Front Desk: +1-305-555-1234")
                        .checkInTime(LocalTime.of(15, 0))
                        .checkOutTime(LocalTime.of(11, 0))
                        .latitude(new BigDecimal("25.7617"))
                        .longitude(new BigDecimal("-80.1918"))
                        .isActive(true)
                        .managedBy("Admin01")
                        .faqs(List.of())  // add FAQs if you have
                        .amenities(List.of(am1,am2,am3,am4,am5,am6,am7,am8,am9, am10,am11,am12,am13,am14,am15,am16,am17,am18)) // add amenities if you have
                        .build();

                Hotel hotel2 = Hotel.builder()
                        .name("Mountain View Inn")
                        .address("456 Alpine Road, Denver, CO")
                        .city("Denver")
                        .state("Colorado")
                        .country("USA")
                        .postalCode("80202")
                        .phoneNumber("+1-720-555-5678")
                        .email("info@mountainviewinn.com")
                        .propertyType(PropertyType.HOTEL)
                        .website("https://www.mountainviewinn.com")
                        .description("Cozy inn with spectacular mountain views and warm hospitality.")
                        .starRating(4)
                        .contactInfo("Reception: +1-720-555-5678")
                        .checkInTime(LocalTime.of(14, 0))
                        .checkOutTime(LocalTime.of(12, 0))
                        .latitude(new BigDecimal("39.7392"))
                        .longitude(new BigDecimal("-104.9903"))
                        .isActive(true)
                        .managedBy("Admin02")
                        .faqs(List.of())  // add FAQs if you have
                        .amenities(List.of(am1,am2,am3,am4,am5,am6,am7,am8,am9, am10,am11,am12,am13,am14,am15,am16,am17,am18))
                        .build();

                Hotel hotel3 = Hotel.builder()
                        .name("City Central Hotel")
                        .address("789 Downtown Blvd, New York, NY")
                        .city("New York")
                        .state("New York")
                        .country("USA")
                        .postalCode("10001")
                        .phoneNumber("+1-212-555-9012")
                        .email("contact@citycentral.com")
                        .propertyType(PropertyType.HOTEL)
                        .website("https://www.citycentral.com")
                        .description("Modern hotel in the heart of the city, close to attractions.")
                        .starRating(3)
                        .contactInfo("Front Desk: +1-212-555-9012")
                        .checkInTime(LocalTime.of(15, 0))
                        .checkOutTime(LocalTime.of(11, 0))
                        .latitude(new BigDecimal("40.7128"))
                        .longitude(new BigDecimal("-74.0060"))
                        .isActive(true)
                        .managedBy("Admin03")
                        .faqs(List.of())  // add FAQs if you have
                        .amenities(List.of(am1,am2,am3,am4,am5,am6,am7,am8,am9, am10,am11,am12,am13,am14,am15,am16,am17,am18))
                        .build();

                Hotel hotel4 = Hotel.builder()
                        .name("Lakeside Boutique")
                        .address("321 Lakeview Drive, Chicago, IL")
                        .city("Chicago")
                        .state("Illinois")
                        .country("USA")
                        .postalCode("60601")
                        .phoneNumber("+1-312-555-3456")
                        .email("stay@lakesideboutique.com")
                        .propertyType(PropertyType.GUEST_HOUSE)
                        .website("https://www.lakesideboutique.com")
                        .description("Charming boutique hotel overlooking the lake.")
                        .starRating(4)
                        .contactInfo("Reception: +1-312-555-3456")
                        .checkInTime(LocalTime.of(15, 0))
                        .checkOutTime(LocalTime.of(11, 0))
                        .latitude(new BigDecimal("41.8781"))
                        .longitude(new BigDecimal("-87.6298"))
                        .isActive(true)
                        .managedBy("Admin04")
                        .faqs(List.of())  // add FAQs if you have
                        .amenities(List.of(am1,am2,am3,am4,am5,am6,am7,am8,am9, am10,am11,am12,am13,am14,am15,am16,am17,am18))
                        .build();

                Hotel hotel5 = Hotel.builder()
                        .name("Cozy Country Lodge")
                        .address("654 Forest Road, Asheville, NC")
                        .city("Asheville")
                        .state("North Carolina")
                        .country("USA")
                        .postalCode("28801")
                        .phoneNumber("+1-828-555-7890")
                        .email("reservations@cozycountrylodge.com")
                        .propertyType(PropertyType.VILLA)
                        .website("https://www.cozycountrylodge.com")
                        .description("Peaceful lodge nestled in the woods with rustic charm.")
                        .starRating(3)
                        .contactInfo("Front Desk: +1-828-555-7890")
                        .checkInTime(LocalTime.of(16, 0))
                        .checkOutTime(LocalTime.of(10, 0))
                        .latitude(new BigDecimal("35.5951"))
                        .longitude(new BigDecimal("-82.5515"))
                        .isActive(true)
                        .managedBy("Admin05")
                        .faqs(List.of())  // add FAQs if you have
                        .amenities(List.of(am1,am2,am3,am4,am5,am6,am7,am8,am9, am10,am11,am12,am13,am14,am15,am16,am17,am18))
                        .build();

                FAQ faq1 = FAQ.builder()
                        .question("What is the check-in time?")
                        .answer("Check-in time is from 3 PM onwards.")
                        .hotel(hotel1)  // reference to a Hotel instance
                        .build();

                FAQ faq2 = FAQ.builder()
                        .question("Is parking available at the hotel?")
                        .answer("Yes, we offer complimentary parking for all guests.")
                        .hotel(hotel2)
                        .build();

                FAQ faq3 = FAQ.builder()
                        .question("Are pets allowed?")
                        .answer("Pets are allowed in designated rooms only. Please contact reception for details.")
                        .hotel(hotel3)
                        .build();

                FAQ faq4 = FAQ.builder()
                        .question("Do you provide airport shuttle service?")
                        .answer("Yes, airport shuttle service is available on request at an additional cost.")
                        .hotel(hotel4)
                        .build();

                FAQ faq5 = FAQ.builder()
                        .question("What is the cancellation policy?")
                        .answer("Free cancellation is available up to 24 hours before the check-in date.")
                        .hotel(hotel5)
                        .build();

            }
        };
    }
}
