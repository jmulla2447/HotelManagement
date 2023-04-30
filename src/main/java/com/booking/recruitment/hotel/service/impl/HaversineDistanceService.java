package com.booking.recruitment.hotel.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public final class HaversineDistanceService {
    public static final double RADIUS = 6371.0088; // Earth's radius Km

    public static Double calculateDistanceBetween(double latitude, double longitude, double cityCentreLatitude, double cityCentreLongitude) {
        Double latDistance = toRad(cityCentreLatitude - latitude);
        Double lonDistance = toRad(cityCentreLongitude - longitude);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(latitude)) * Math.cos(toRad(cityCentreLatitude)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RADIUS * c;
    }

    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }
}
