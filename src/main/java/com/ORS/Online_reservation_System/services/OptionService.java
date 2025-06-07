package com.ORS.Online_reservation_System.services;

import com.ORS.Online_reservation_System.model.Option;
import com.ORS.Online_reservation_System.model.OptionCategory;
import com.ORS.Online_reservation_System.model.OptionDetails;

import java.util.List;
import java.util.Optional;

public interface OptionService {

    Option saveOption(Option option);

    Optional<Option> findById(Integer id);

    List<Option> findAllOptions();

    List<Option> findAvailableOptions();

    List<Option> findByCategory(OptionCategory category);

    List<Option> findAvailableByCategory(OptionCategory category);

    List<Option> findByPriceRange(Double maxPrice);

    List<Option> searchOptions(String keyword);

    Option updateOption(Integer id, Option option);

    Option updateOptionDetails(Integer id, OptionDetails optionDetails);

    void toggleAvailability(Integer id);

    boolean deleteOption(Integer id);

    Double calculateOptionPrice(Integer id);

}
