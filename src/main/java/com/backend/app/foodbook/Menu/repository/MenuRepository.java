package com.backend.app.foodbook.Menu.repository;

import com.backend.app.foodbook.Menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
