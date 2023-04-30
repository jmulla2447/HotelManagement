package com.booking.recruitment.hotel.service.impl;

import com.booking.recruitment.hotel.exception.BadRequestException;
import com.booking.recruitment.hotel.model.DistanceHotel;
import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.repository.HotelRepository;
import com.booking.recruitment.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class DefaultHotelService implements HotelService {
    private final HotelRepository hotelRepository;


    @Autowired
    DefaultHotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public List<Hotel> getHotelsByCity(Long cityId) {
        return hotelRepository.findAll().stream()
                .filter((hotel) -> cityId.equals(hotel.getCity().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Hotel createNewHotel(Hotel hotel) {
        if (hotel.getId() != null) {
            throw new BadRequestException("The ID must not be provided when creating a new Hotel");
        }

        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel getHotelById(Long hotelId) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);
        if (optionalHotel.isPresent()) {
            return optionalHotel.get();
        } else {
            throw new IllegalStateException("New Hotel has not been saved in the repository");
        }
    }

    @Override
    public Hotel deleteHotelById(Long hotelId) {
        Hotel currentHotel = getHotelById(hotelId);
        if (!Objects.isNull(currentHotel)) {
            currentHotel.setDeleted(false);
        }
        return hotelRepository.save(currentHotel);
    }

    @Override
    public List<Hotel> searchHotels(Long cityId, String sortBy) {
        List<Hotel> hotels = hotelRepository.findByCityId(cityId);
        if (sortBy != null && sortBy.equalsIgnoreCase("distance")) {//factory
            hotels = hotels.stream()
                    .map(hotel -> new DistanceHotel(hotel, HaversineDistanceService.calculateDistanceBetween(hotel.getLatitude(),hotel.getLongitude()
                            , hotel.getCity().getCityCentreLatitude(),hotel.getCity().getCityCentreLongitude())))
                    .sorted(Comparator.comparing(DistanceHotel::getDistance))
                    .map(DistanceHotel::getHotel)
                    .collect(Collectors.toList());
        }
        return hotels;
    }
}
