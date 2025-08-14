package org.example.hall_university_menu.repository;

import org.example.hall_university_menu.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);

    boolean existsByUserIdAndMenuId(Long userId, Long menuId);

    void deleteAllByMenuId(Long menuId);

}
