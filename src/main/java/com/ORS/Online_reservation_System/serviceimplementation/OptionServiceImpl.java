package com.ORS.Online_reservation_System.serviceimplementation;

import com.ORS.Online_reservation_System.model.Option;
import com.ORS.Online_reservation_System.model.OptionCategory;
import com.ORS.Online_reservation_System.model.OptionDetails;
import com.ORS.Online_reservation_System.repositories.OptionRepository;
import com.ORS.Online_reservation_System.services.OptionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OptionServiceImpl implements OptionService {

    private final OptionRepository optionRepository;

    @Autowired
    public OptionServiceImpl(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    @Override
    @Transactional
    public Option saveOption(Option option) {
        // Set default availability if not provided
        if (option.getAvailability() == null) {
            option.setAvailability(true);
        }
        return optionRepository.save(option);
    }

    @Override
    public Optional<Option> findById(Integer id) {
        return optionRepository.findById(id);
    }

    @Override
    public List<Option> findAllOptions() {
        return optionRepository.findAll();
    }

    @Override
    public List<Option> findAvailableOptions() {
        return optionRepository.findByAvailabilityTrue();
    }

    @Override
    public List<Option> findByCategory(OptionCategory category) {
        return optionRepository.findByCategory(category);
    }

    @Override
    public List<Option> findAvailableByCategory(OptionCategory category) {
        return optionRepository.findByCategoryAndAvailabilityTrue(category);
    }

    @Override
    public List<Option> findByPriceRange(Double maxPrice) {
        return optionRepository.findByPriceLessThanEqual(maxPrice);
    }

    @Override
    public List<Option> searchOptions(String keyword) {
        return optionRepository.searchByKeyword(keyword);
    }

    @Override
    @Transactional
    public Option updateOption(Integer id, Option option) {
        return optionRepository.findById(id)
                .map(existingOption -> {
                    existingOption.setName(option.getName());
                    existingOption.setDescription(option.getDescription());
                    existingOption.setPrice(option.getPrice());
                    existingOption.setCategory(option.getCategory());
                    existingOption.setAvailability(option.getAvailability());
                    return optionRepository.save(existingOption);
                })
                .orElseThrow(() -> new RuntimeException("Option not found with id: " + id));
    }

    @Override
    @Transactional
    public Option updateOptionDetails(Integer id, OptionDetails optionDetails) {
        return optionRepository.findById(id)
                .map(existingOption -> {
                    existingOption.updateDetails(optionDetails);
                    return optionRepository.save(existingOption);
                })
                .orElseThrow(() -> new RuntimeException("Option not found with id: " + id));
    }

    @Override
    @Transactional
    public void toggleAvailability(Integer id) {
        optionRepository.findById(id)
                .ifPresent(option -> {
                    option.setAvailability(!option.getAvailability());
                    optionRepository.save(option);
                });
    }

    @Override
    @Transactional
    public boolean deleteOption(Integer id) {
        if (optionRepository.existsById(id)) {
            optionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Double calculateOptionPrice(Integer id) {
        return optionRepository.findById(id)
                .map(Option::calculatePrice)
                .orElseThrow(() -> new RuntimeException("Option not found with id: " + id));
    }

}
