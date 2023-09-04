package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service

public class HotelManagementService {

    HotelManagmentRepository hotelManagmentRepository = new HotelManagmentRepository();

    public String addHotel(Hotel hotel) {
        if(hotel.getHotelName() == null || hotel == null){
            return "FAILURE";
        }
        return hotelManagmentRepository.addHotel(hotel);
    }

    public Integer addUser(User user) {
        return  hotelManagmentRepository.addUser(user);
    }

    public String getHotelWithMostFacilities() {
        return hotelManagmentRepository.getHotelWithMostFacilities();
    }

    public int bookARoom(Booking booking) {
        String bookingid = UUID.randomUUID().toString();
        booking.setBookingId(bookingid);
        return hotelManagmentRepository.bookARoom(booking);
    }

    public int getBookings(Integer aadharCard) {
        return hotelManagmentRepository.getBookings(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        return hotelManagmentRepository.updateFacilities(newFacilities,hotelName);
    }
}