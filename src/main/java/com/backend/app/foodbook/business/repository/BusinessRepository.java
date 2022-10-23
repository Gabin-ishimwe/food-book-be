package com.backend.app.foodbook.business.repository;

import com.backend.app.foodbook.business.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    Business findByBusinessName(String businessName);
}
