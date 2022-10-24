package com.backend.app.foodbook.business.service;

import com.backend.app.foodbook.auth.entity.User;
import com.backend.app.foodbook.auth.repository.UserRepository;
import com.backend.app.foodbook.business.dto.BusinessDto;
import com.backend.app.foodbook.business.entity.Business;
import com.backend.app.foodbook.business.repository.BusinessRepository;
import com.backend.app.foodbook.exception.NotFoundException;
import com.backend.app.foodbook.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BussinessService {

    private final BusinessRepository businessRepository;

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    @Autowired
    BussinessService(BusinessRepository businessRepository, JwtUtil jwtUtil, UserRepository userRepository) {
        this.businessRepository = businessRepository;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public Business createBusiness(BusinessDto businessDto, String token) throws Exception {
        String getUserEmail = jwtUtil.getUserNameFromToken(token);
        User findUser = userRepository.findByEmail(getUserEmail);
        Business findBusiness = businessRepository.findByBusinessName(businessDto.getBusinessName());
        if (findBusiness != null) {
            throw new Exception("Business with name " + businessDto.getBusinessName() + " arleady exists");
        }
        Business business = new Business(
                null,
                businessDto.getBusinessName(),
                businessDto.getBusinessDescription(),
                businessDto.getBusinessEmail(),
                businessDto.getBusinessContact()
        );

        Business businessCreated = businessRepository.save(business);

        findUser.getBusinesses().add(business);
        userRepository.save(findUser);

        return businessCreated;
    }

    public Business editBusiness(BusinessDto businessDto, Long businessId) throws NotFoundException {
        Business findBusiness = businessRepository.findById(businessId).orElseThrow(() -> new NotFoundException("Business doesn't exist"));

        if (!Objects.equals(findBusiness.getBusinessName(), businessDto.getBusinessName()) &&
                businessDto.getBusinessName() != null) {
            findBusiness.setBusinessName(businessDto.getBusinessName());
        }
        if (!Objects.equals(findBusiness.getBusinessDescription(), businessDto.getBusinessDescription()) &&
                businessDto.getBusinessDescription() != null) {
            findBusiness.setBusinessDescription(businessDto.getBusinessDescription());
        }

        if (!Objects.equals(findBusiness.getBusinessEmail(), businessDto.getBusinessEmail()) &&
                businessDto.getBusinessEmail() != null) {
            findBusiness.setBusinessEmail(businessDto.getBusinessEmail());
        }

        if (!Objects.equals(findBusiness.getBusinessContact(), businessDto.getBusinessContact()) &&
                businessDto.getBusinessContact() != null) {
            findBusiness.setBusinessContact(businessDto.getBusinessContact());
        }


        return businessRepository.save(findBusiness);

    }

    public Business getOneBusiness(Long businessId) throws NotFoundException {
        return businessRepository.findById(businessId).orElseThrow(() -> new NotFoundException("Business doesn't exist"));
    }

    public List<Business> getAllBusiness() {
        return businessRepository.findAll();
    }
}
