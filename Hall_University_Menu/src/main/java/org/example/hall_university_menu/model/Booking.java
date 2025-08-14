package org.example.hall_university_menu.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "booking")
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "menu_id")
    private Long menuId;

    private String status;
}
