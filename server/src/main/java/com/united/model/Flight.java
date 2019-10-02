package com.united.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

/**
 * Flight data object
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Flight {
    public Integer flightNumber;
    public String carrier;
    public String origin;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public Date departure;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public Date arrival;

    public String destination;
    public String aircraft;
    public int distance;
    public String travelTime;
    public String status;
}
