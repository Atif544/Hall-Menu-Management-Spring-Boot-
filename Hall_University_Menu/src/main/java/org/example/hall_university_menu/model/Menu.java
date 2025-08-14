package org.example.hall_university_menu.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "menu")
@Data
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String day;

    @Column(name = "meal_type")
    private String mealType;

    @Column(name = "food_items") // ✅ DB column er name
    private String items;

    private double cost;

    private String type; // optional
}
