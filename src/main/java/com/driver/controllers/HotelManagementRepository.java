package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HotelManagementRepository {

    Map<String , Hotel> hotelDB = new HashMap<>();
    Map<Integer , User> userDB = new HashMap<>();
    Map<String , Booking> bookingDB = new HashMap<>();

    public boolean addHotel(Hotel hotel) {
        if(hotelDB.containsKey(hotel.getHotelName())){
            return false;
        }

        hotelDB.put(hotel.getHotelName() , hotel);
        return true;
    }

    public Integer addUser(User user) {
        userDB.put(user.getaadharCardNo() , user);
        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities() {
        List<String> hotels = new ArrayList<>();
        int noOfFacilities = 0;
        for(String name : hotelDB.keySet()){
            Hotel hotel = hotelDB.get(name);
            List<Facility> facilities = hotel.getFacilities();
            if(facilities.size() > noOfFacilities){
                hotels = new ArrayList<>();
                hotels.add(name);
                noOfFacilities = facilities.size();
            }else if(facilities.size() > 0 && facilities.size() == noOfFacilities){
                hotels.add(name);
            }
        }

        if(hotels.isEmpty()) return "";

        Collections.sort(hotels);
        return hotels.get(0);
    }

    public int bookARoom(String bookingId, Booking booking) {

        bookingDB.put(bookingId , booking);

        Hotel hotel = hotelDB.get(booking.getHotelName());

        int noOfRoomsAvailable = hotel.getAvailableRooms();
        int noOfRoomsNeed = booking.getNoOfRooms();

        if(noOfRoomsNeed > noOfRoomsAvailable) return -1;

        int priceOfTheRooms = hotel.getPricePerNight();

        int totalPrice = priceOfTheRooms * noOfRoomsNeed;
        booking.setAmountToBePaid(totalPrice);

        hotel.setAvailableRooms(noOfRoomsAvailable - noOfRoomsNeed);
        return totalPrice;
    }


    public int getBookings(Integer aadharCard) {

        int count = 0;
        for(String bookingId : bookingDB.keySet()){
            Booking booking = bookingDB.get(bookingId);
            if(booking.getBookingAadharCard() == aadharCard){
                count += booking.getNoOfRooms();
            }
        }

        return count;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {

        Hotel hotel = hotelDB.get(hotelName);

        List<Facility> hotelFacilities = hotel.getFacilities();

        for(Facility facility : newFacilities){
            if(!hotelFacilities.contains(facility)){
                hotelFacilities.add(facility);
            }
        }

        hotel.setFacilities(hotelFacilities);
        return hotel;
    }
}