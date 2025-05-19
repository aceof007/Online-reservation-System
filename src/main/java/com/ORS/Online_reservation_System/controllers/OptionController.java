package com.ORS.Online_reservation_System.controllers;

import com.ORS.Online_reservation_System.dto.OptionDTO;
import com.ORS.Online_reservation_System.model.Option;
import com.ORS.Online_reservation_System.model.OptionCategory;
import com.ORS.Online_reservation_System.model.OptionDetails;
import com.ORS.Online_reservation_System.services.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/options")
public class OptionController {

    private final OptionService optionService;

    @Autowired
    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @PostMapping
    public ResponseEntity<Option> createOption(@RequestBody OptionDTO optionDTO) {
        Option option = convertToEntity(optionDTO);
        Option savedOption = optionService.saveOption(option);
        return new ResponseEntity<>(savedOption, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Option> getOptionById(@PathVariable Integer id) {
        Optional<Option> option = optionService.findById(id);
        return option.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Option>> getAllOptions() {
        List<Option> options = optionService.findAllOptions();
        return new ResponseEntity<>(options, HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Option>> getAvailableOptions() {
        List<Option> options = optionService.findAvailableOptions();
        return new ResponseEntity<>(options, HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Option>> getOptionsByCategory(@PathVariable OptionCategory category) {
        List<Option> options = optionService.findByCategory(category);
        return new ResponseEntity<>(options, HttpStatus.OK);
    }

    @GetMapping("/category/{category}/available")
    public ResponseEntity<List<Option>> getAvailableOptionsByCategory(@PathVariable OptionCategory category) {
        List<Option> options = optionService.findAvailableByCategory(category);
        return new ResponseEntity<>(options, HttpStatus.OK);
    }

    @GetMapping("/price")
    public ResponseEntity<List<Option>> getOptionsByPriceRange(@RequestParam Double maxPrice) {
        List<Option> options = optionService.findByPriceRange(maxPrice);
        return new ResponseEntity<>(options, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Option>> searchOptions(@RequestParam String keyword) {
        List<Option> options = optionService.searchOptions(keyword);
        return new ResponseEntity<>(options, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Option> updateOption(@PathVariable Integer id, @RequestBody OptionDTO optionDTO) {
        Option option = convertToEntity(optionDTO);
        Option updatedOption = optionService.updateOption(id, option);
        return new ResponseEntity<>(updatedOption, HttpStatus.OK);
    }

    @PatchMapping("/{id}/details")
    public ResponseEntity<Option> updateOptionDetails(@PathVariable Integer id, @RequestBody OptionDTO optionDTO) {
        OptionDetails details = new OptionDetails(
                optionDTO.getOptionId(),
                optionDTO.getName(),
                optionDTO.getDescription(),
                optionDTO.getPrice(),
                optionDTO.getCategory()
        );

        Option updatedOption = optionService.updateOptionDetails(id, details);
        return new ResponseEntity<>(updatedOption, HttpStatus.OK);
    }

    @PatchMapping("/{id}/toggle-availability")
    public ResponseEntity<Void> toggleAvailability(@PathVariable Integer id) {
        optionService.toggleAvailability(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOption(@PathVariable Integer id) {
        boolean deleted = optionService.deleteOption(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/price")
    public ResponseEntity<Double> calculateOptionPrice(@PathVariable Integer id) {
        Double price = optionService.calculateOptionPrice(id);
        return new ResponseEntity<>(price, HttpStatus.OK);
    }

    private Option convertToEntity(OptionDTO optionDTO) {
        Option option = new Option();
        option.setName(optionDTO.getName());
        option.setDescription(optionDTO.getDescription());
        option.setPrice(optionDTO.getPrice());
        option.setCategory(optionDTO.getCategory());
        option.setAvailability(optionDTO.getAvailability() != null ? optionDTO.getAvailability() : true);
        return option;
    }
}
