package com.booking.recruitment.hotel.controller;

import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {
  private final HotelService hotelService;

  @Autowired
  public HotelController(HotelService hotelService) {
    this.hotelService = hotelService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Hotel> getAllHotels() {
    return hotelService.getAllHotels();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Hotel createHotel(@RequestBody Hotel hotel) {
    return hotelService.createNewHotel(hotel);
  }

  @GetMapping("/{hotelId}")
  public Hotel getHotelById(@PathVariable Long hotelId){
    return hotelService.getHotelById(hotelId);
  }

  @DeleteMapping("{hotelId}")
  public ResponseEntity<Void> deleteHotelById(@PathVariable Long hotelId) {
    hotelService.getHotelById(hotelId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{cityId}")
  public List<Hotel> searchHotels(@PathVariable Long cityId, @RequestParam(required = false) String sortBy) {
    return hotelService.searchHotels(cityId, sortBy);
  }
}
