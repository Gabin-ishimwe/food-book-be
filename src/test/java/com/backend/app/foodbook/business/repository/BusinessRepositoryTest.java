package com.backend.app.foodbook.business.repository;

import com.backend.app.foodbook.business.entity.Business;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BusinessRepositoryTest {

    @Autowired
    private BusinessRepository businessRepository;

    @Test
    void itShouldTestFindByBusinessName() {
        Business business = Business.builder()
                .businessName("BWOK")
                .businessDescription("chinese restaurant")
                .businessEmail("bwok@gmail.com")
                .businessContact("0787834201")
                .build();
        var createdBusiness = businessRepository.save(business);
        var findBusinessByName = businessRepository.findByBusinessName(business.getBusinessName());
        assertThat(findBusinessByName.getBusinessName()).isEqualTo(createdBusiness.getBusinessName());

    }
}