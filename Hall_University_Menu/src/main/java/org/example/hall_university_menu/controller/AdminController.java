package org.example.hall_university_menu.controller;

import org.example.hall_university_menu.model.*;
import org.example.hall_university_menu.repository.*;
import org.example.hall_university_menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private MenuService menuService;
    @Autowired private BookingRepository bookingRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private FeedbackRepository feedbackRepo;


    @GetMapping("/menus")
    public String listMenus(Model model){
        model.addAttribute("menus", menuService.getAllMenus());
        model.addAttribute("menu", new Menu());
        return "admin/menu_list";
    }

    @GetMapping("/menu/edit/{id}")
    public String editMenu(@PathVariable Long id, Model model) {
        model.addAttribute("menu", menuService.getMenuById(id));
        model.addAttribute("menus", menuService.getAllMenus());
        return "admin/menu_list";
    }

    @PostMapping("/menu/save")
    public String saveMenu(@ModelAttribute Menu menu){
        menuService.saveMenu(menu);
        return "redirect:/admin/menus";
    }

    @GetMapping("/menu/delete/{id}")
    public String deleteMenu(@PathVariable Long id){

        bookingRepo.deleteAllByMenuId(id);

        menuService.deleteMenu(id);
        return "redirect:/admin/menus";
    }


    @GetMapping("/bookings")
    public String viewBookings(Model model){
        List<Booking> bookings = bookingRepo.findAll();
        List<BookingAdminView> bookingViews = new ArrayList<>();
        double totalCost = 0;

        for (Booking b : bookings) {
            User user = userRepo.findById(b.getUserId()).orElse(null);
            Menu menu = menuService.getMenuById(b.getMenuId());

            if (menu != null) {
                double cost = menu.getCost();
                totalCost += cost;
                bookingViews.add(new BookingAdminView(
                        b.getId(),
                        user != null ? user.getUsername() : "Unknown",
                        menu.getDay(),
                        menu.getMealType(),
                        cost
                ));
            }
        }

        model.addAttribute("bookingViews", bookingViews);
        model.addAttribute("totalCost", totalCost);
        return "admin/bookings";
    }


    @GetMapping("/feedbacks")
    public String viewAllFeedbacks(Model model) {
        List<Feedback> feedbacks = feedbackRepo.findAll();
        List<FeedbackAdminView> views = new ArrayList<>();

        for (Feedback f : feedbacks) {
            User user = userRepo.findById(f.getUserId()).orElse(null);
            Menu menu = menuService.getMenuById(f.getMenuId());

            views.add(new FeedbackAdminView(
                    f.getId(),
                    user != null ? user.getUsername() : "Unknown",
                    menu != null ? menu.getDay() + " - " + menu.getMealType() : "Unknown",
                    f.getRating(),
                    f.getComment()
            ));
        }

        model.addAttribute("feedbackViews", views);
        return "admin/feedbacks";
    }
}


class BookingAdminView {
    private Long bookingId;
    private String username;
    private String menuInfo;
    private double cost;

    public BookingAdminView(Long bookingId, String username, String day, String mealType, double cost) {
        this.bookingId = bookingId;
        this.username = username;
        this.menuInfo = day + " - " + mealType;
        this.cost = cost;
    }
    public Long getBookingId() { return bookingId; }
    public String getUsername() { return username; }
    public String getMenuInfo() { return menuInfo; }
    public double getCost() { return cost; }
}

class FeedbackAdminView {
    private Long feedbackId;
    private String username;
    private String menuInfo;
    private int rating;
    private String comment;

    public FeedbackAdminView(Long feedbackId, String username, String menuInfo, int rating, String comment) {
        this.feedbackId = feedbackId;
        this.username = username;
        this.menuInfo = menuInfo;
        this.rating = rating;
        this.comment = comment;
    }
    public Long getFeedbackId() { return feedbackId; }
    public String getUsername() { return username; }
    public String getMenuInfo() { return menuInfo; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
}