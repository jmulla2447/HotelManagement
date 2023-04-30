package com.booking.recruitment.hotel.model;

public class DistanceHotel {
    private final Hotel hotel;
    private final double distance;

    public DistanceHotel(Hotel hotel, double distance) {
        this.hotel = hotel;
        this.distance = distance;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public double getDistance() {
        return distance;
    }
}

