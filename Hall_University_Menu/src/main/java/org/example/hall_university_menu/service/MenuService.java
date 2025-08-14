package org.example.hall_university_menu.service;

import org.example.hall_university_menu.model.Menu;
import org.example.hall_university_menu.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepo;


    public List<Menu> getAllMenus() {
        return menuRepo.findAll();
    }


    public Menu saveMenu(Menu menu) {
        return menuRepo.save(menu);
    }


    public void deleteMenu(Long id) {
        menuRepo.deleteById(id);
    }


    public Menu getMenuById(Long id) {
        return menuRepo.findById(id).orElse(null);
    }


    public List<Menu> getMenusByDay(String day) {
        return menuRepo.findByDay(day);
    }
}
