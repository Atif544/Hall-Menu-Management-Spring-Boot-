package org.example.hall_university_menu.controller;

import org.example.hall_university_menu.model.Feedback;
import org.example.hall_university_menu.model.Booking;
import org.example.hall_university_menu.model.User;
import org.example.hall_university_menu.repository.FeedbackRepository;
import org.example.hall_university_menu.repository.BookingRepository;
import org.example.hall_university_menu.repository.UserRepository;
import org.example.hall_university_menu.service.BookingService;
import org.example.hall_university_menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired private MenuService menuService;
    @Autowired private BookingService bookingService;
    @Autowired private FeedbackRepository feedbackRepo;
    @Autowired private BookingRepository bookingRepo;
    @Autowired private UserRepository userRepo;


    @GetMapping("/menus")
    public String viewMenu(Model model) {
        model.addAttribute("menus", menuService.getAllMenus());
        model.addAttribute("users", userRepo.findAll()); // for dropdown
        return "student/view_menu";
    }


    @PostMapping("/book")
    public String bookMeal(@RequestParam Long userId, @RequestParam Long menuId) {
        bookingService.bookMeal(userId, menuId);
        return "redirect:/student/bookings?userId=" + userId;
    }


    @GetMapping("/bookings")
    public String viewBookings(@RequestParam Long userId, Model model) {
        List<Booking> bookings = bookingRepo.findByUserId(userId);
        double totalCost = bookingService.calculateTotalCost(userId);

        User user = userRepo.findById(userId).orElse(null);
        String username = (user != null) ? user.getUsername() : "Unknown";

        model.addAttribute("bookings", bookings);
        model.addAttribute("totalCost", totalCost);
        model.addAttribute("username", username);
        model.addAttribute("userId", userId);

        return "student/bookings";
    }


    @GetMapping("/feedback")
    public String feedbackForm(@RequestParam Long userId,
                               @RequestParam Long menuId,
                               Model model) {
        boolean booked = bookingRepo.existsByUserIdAndMenuId(userId, menuId);
        if (!booked) {
            model.addAttribute("error", "You can only give feedback on menus you booked!");
            return "student/bookings";
        }

        model.addAttribute("menu", menuService.getMenuById(menuId));
        model.addAttribute("userId", userId);
        return "student/feedback";
    }


    @PostMapping("/feedback")
    public String submitFeedback(@RequestParam Long userId,
                                 @RequestParam Long menuId,
                                 @RequestParam int rating,
                                 @RequestParam String comment) {
        Feedback f = new Feedback();
        f.setUserId(userId);
        f.setMenuId(menuId);
        f.setRating(rating);
        f.setComment(comment);
        feedbackRepo.save(f);

        return "redirect:/student/bookings?userId=" + userId;
    }
}