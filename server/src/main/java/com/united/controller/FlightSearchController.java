package com.united.controller;

import com.united.model.Flight;
import com.united.service.FlightSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * REST API for finding flights
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*")
public class FlightSearchController {

    private final FlightSearchService flightSearchService;

    public FlightSearchController(FlightSearchService flightSearchService) {
        this.flightSearchService = flightSearchService;
    }

    /**
     * REST API for performing a flight search
     *
     * @param query flight search query
     * @return List of flights matching the query
     */
    @GetMapping("/search")
    public List<Flight> performSearch(@RequestParam Optional<String> query) {
        if (!query.isPresent()) {
            return Collections.emptyList();
        } else {
            return this.flightSearchService.performFlightSearch(query.get());
        }
    }
}
