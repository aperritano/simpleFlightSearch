package com.united.service;

import com.united.model.Flight;

import java.util.List;

/**
 * Interface for FlightSearchServices
 */
public interface FlightSearchService {
    /**
     * Perform a flight search
     *
     * @param params query params for flights
     * @return results of the query
     */
    List<Flight> performFlightSearch(String params);
}
