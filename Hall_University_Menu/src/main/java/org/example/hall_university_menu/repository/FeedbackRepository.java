package org.example.hall_university_menu.repository;

import org.example.hall_university_menu.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByMenuId(Long menuId);
}
