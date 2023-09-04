package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository

public class HotelManagmentRepository {

    HashMap<String , Hotel> Hoteldb = new HashMap<>();
    HashMap<Integer , User> Userdb = new HashMap<>();

    HashMap<String , Booking> Bookingdb = new HashMap<>();

    public String addHotel(Hotel hotel) {
        if(Hoteldb.containsKey(hotel.getHotelName()) == true){
            return "FAILURE";
        }
        else {
            Hoteldb.put(hotel.getHotelName() , hotel);
            return "SUCCESS";
        }
    }

    public Integer addUser(User user) {
        Userdb.put(user.getaadharCardNo() , user);
        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities() {
        String hotel = "";
        int fac = 1;
        for(String hotelname : Hoteldb.keySet()){
            int temp = Hoteldb.get(hotelname).getFacilities().size();
            if(temp > fac){
                hotel = hotelname;
                fac = temp;
            }
            else if(temp == fac && Hoteldb.get(hotelname).getHotelName().compareTo(hotel) < 0){
                hotel = hotelname;
                fac = temp;
            }
        }
        return hotel;
    }

    public int bookARoom(Booking booking) {
        String hotelname = booking.getHotelName();
        int room = booking.getNoOfRooms();
        if(Hoteldb.get(hotelname).getAvailableRooms() >= room){
            booking.setAmountToBePaid(Hoteldb.get(hotelname).getPricePerNight()*room);
            Bookingdb.put(booking.getBookingId(), booking);
            Hoteldb.get(hotelname).setAvailableRooms(Hoteldb.get(hotelname).getAvailableRooms()-room);
            return booking.getAmountToBePaid();
        }
        else {
            return -1;
        }
    }

    public int getBookings(Integer aadharCard) {
        int ans = 0;
        for(String bookingid : Bookingdb.keySet()){
            if(Bookingdb.get(bookingid).getBookingAadharCard() == aadharCard){
                ans++;
            }
        }
        return ans;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        List<Facility> existingfacilityList = Hoteldb.get(hotelName).getFacilities();
        for(Facility facility : newFacilities){
            if(!existingfacilityList.contains(facility))
            {
                existingfacilityList.add(facility);
            }
        }
        Hoteldb.get(hotelName).setFacilities(existingfacilityList);
        return Hoteldb.get(hotelName);

    }
}