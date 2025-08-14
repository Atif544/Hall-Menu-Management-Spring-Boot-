package org.example.hall_university_menu.service;

import org.example.hall_university_menu.model.Booking;
import org.example.hall_university_menu.model.Menu;
import org.example.hall_university_menu.repository.BookingRepository;
import org.example.hall_university_menu.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired private BookingRepository bookingRepo;
    @Autowired private MenuRepository menuRepo;

    public Booking bookMeal(Long userId, Long menuId) {
        Booking b = new Booking();
        b.setUserId(userId);
        b.setMenuId(menuId);
        b.setStatus("BOOKED");
        return bookingRepo.save(b);  // Data insert here
    }

    public double calculateTotalCost(Long userId) {
        List<Booking> bookings = bookingRepo.findByUserId(userId);
        return bookings.stream()
                .mapToDouble(b -> menuRepo.findById(b.getMenuId())
                        .map(m -> m.getCost())   // safe check
                        .orElse(0.0))
                .sum();
    }
}
