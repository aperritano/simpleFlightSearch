package com.united.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.united.model.Flight;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Concrete implementation of a flightSearchService
 */
@Service
public class FlightSearchServiceLocal implements FlightSearchService {

    private static List<Flight> localFlightDB = null;

    public FlightSearchServiceLocal() {
        super();
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Flight>> typeReference = new TypeReference<List<Flight>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/flight-sample.json");
        try {
            localFlightDB = mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Returns the query results with the syntax:
     * (Flight Number || (Origin && Destination)) && Date
     * <p>
     * It uses regex to parse the query and perform a search on the
     * database.
     * <p>
     * If the query is in the proper syntax:
     * Scenario 1: origin && destination && date
     * e.g. IAH ORD 2018-05-05
     * <p>
     * Scenario 2: flight number && date
     * e.g. 2005 2018-05-05
     * <p>
     * If the query is invalid return an empty list.
     * e.g. BAD: ORD 2018-05-05 Good: IAH ORD 2018-05-05
     * <p>
     * TODO: can we brute force this with a monster regex expression? "(\d{1,4}|([a-zA-Z]{3}) ([a-zA-Z]{3})) (\d{4}[-\/]\d{1,2}[-\/]\d{1,2})" - works, but needs a proper param order
     *
     * @param params The query string from user input
     * @return The results of the query from the param string, default empty list
     */
    @Override
    public List<Flight> performFlightSearch(String params) {
        if (params == null)
            return Collections.emptyList();

        final String findWordsRegex = "[\\w-\\/]+";
        Pattern pattern = Pattern.compile(findWordsRegex);
        Matcher matcher = pattern.matcher(params);
        List<String> matched = new ArrayList<>();

        while (matcher.find()) {
            matched.add(matcher.group(0));
        }

        switch (matched.size()) {
            case 2:
                return processFlightNumberAndDate(matched);
            case 3:
                return processAirportCodesAndDate(matched);
            default:
                return Collections.emptyList();
        }
    }

    /**
     * Scenario 1. Return flights using origin, destination, and date
     * Possible input order: origin dest date, date origin dest, origin date dest
     *
     * @param params query params
     * @return the result of the query, default empty list
     */
    private List<Flight> processAirportCodesAndDate(List<String> params) {
        if (params == null || params.isEmpty())
            return Collections.emptyList();

        HashMap<String, String> query = new HashMap<>();
        for (String p : params) {
            if (isValidDate(p)) {
                query.put("date", p);
            } else if (isValidAirportCode(p)) {
                if (query.containsKey("origin")) {
                    query.put("dest", p);
                } else {
                    query.put("origin", p);
                }
            }
        }

        // if not all the params ended up being valid - eject
        if (query.keySet().size() < 3) {
            return Collections.emptyList();
        }

        try {
            Date foundDate = this.convertStringToDate(query.get("date"));
            String origin = query.get("origin");
            String dest = query.get("dest");

            if (!origin.equals(dest)) {
                return localFlightDB.stream()
                        .filter(flight -> (flight.getOrigin().equalsIgnoreCase(origin) &&
                                flight.getDestination().equalsIgnoreCase(dest) &&
                                (DateUtils.isSameDay(flight.getArrival(), foundDate) || DateUtils.isSameDay(flight.getDeparture(), foundDate))))
                        .collect(Collectors.toList());
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return Collections.emptyList();
    }

    /**
     * Scenario 2. Return flights using flightNumber and date
     * Possible input order: flightNumber date, date flightNumber
     *
     * @param params query params
     * @return the result of the query
     */
    private List<Flight> processFlightNumberAndDate(List<String> params) {
        if (params == null || params.isEmpty())
            return Collections.emptyList();

        HashMap<String, String> query = new HashMap<>();
        for (String p : params) {
            if (isValidDate(p)) {
                query.put("date", p);
            } else if (isValidFlightNumber(p)) {
                query.put("flightNumber", p);
            }
        }

        // if not all the params ended up being valid - eject
        if (query.keySet().size() < 2) {
            return Collections.emptyList();
        }

        try {
            Date foundDate = this.convertStringToDate(query.get("date"));
            Integer flightNumber = Integer.valueOf(query.get("flightNumber"));

            return localFlightDB.stream()
                    .filter(flight -> flight.getFlightNumber().equals(flightNumber) &&
                            (DateUtils.isSameDay(flight.getArrival(), foundDate) || DateUtils.isSameDay(flight.getDeparture(), foundDate)))
                    .collect(Collectors.toList());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    /**
     * Check if the string is a valid flight number.
     * Flight numbers have 3-4 digits, e.g. 2005, 605
     * However, the user could enter 0625 or 625.
     *
     * @param flightNumber The flight number to check
     * @return Whether it is a valid flight number
     */
    private boolean isValidFlightNumber(String flightNumber) {
        if (flightNumber == null)
            return false;
        return flightNumber.matches("\\d{1,4}");
    }

    /**
     * Check if we have a valid airport code
     *
     * @param airportCode The airport code to check
     * @return Whether it is a valid airport code
     */
    protected boolean isValidAirportCode(String airportCode) {
        if (airportCode == null)
            return false;

        return airportCode.matches("[a-zA-Z]{3}");
    }

    /**
     * Figure out if the param is a date in the valid the format 2018-01-01
     * TODO: On 2018-5-5 pad with zeros, e.g. 2018-05-05
     *
     * @param date The date to check
     * @return Whether it is a valid date
     */
    private boolean isValidDate(String date) {
        if (date == null)
            return false;

        return date.matches("\\d{4}[-]\\d{1,2}[-]\\d{1,2}");
    }

    /**
     * Convert a string to a date, must have the format yyyy-MM-DD
     * TODO: detect and convert any date format
     *
     * @param date The string to convert
     * @return The converted date
     * @throws ParseException
     */
    private Date convertStringToDate(String date) throws ParseException {
        if (date == null)
            return null;

        return new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
    }
}
