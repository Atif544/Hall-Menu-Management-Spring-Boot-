package org.example.hall_university_menu.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long menuId;
    private int rating;
    private String comment;
}
