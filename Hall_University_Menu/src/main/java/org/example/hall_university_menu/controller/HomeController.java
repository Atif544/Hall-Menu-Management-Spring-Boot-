package org.example.hall_university_menu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Root homepage
    @GetMapping("/")
    public String homePage() {
        return "index"; // templates/index.html
    }

    // Student dashboard
    @GetMapping("/student/home")
    public String studentHome() {
        return "student_home"; // templates/student_home.html
    }

    // Admin dashboard
    @GetMapping("/admin/home")
    public String adminHome() {
        return "admin_home"; // templates/admin_home.html
    }
}
