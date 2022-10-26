package com.backend.app.foodbook.data;

import com.backend.app.foodbook.auth.entity.User;
import com.backend.app.foodbook.auth.repository.UserRepository;
import com.backend.app.foodbook.business.entity.Business;
import com.backend.app.foodbook.business.repository.BusinessRepository;
import com.backend.app.foodbook.exception.NotFoundException;
import com.backend.app.foodbook.meal.entity.Meal;
import com.backend.app.foodbook.meal.repository.MealRepository;
import com.backend.app.foodbook.role.entity.Role;
import com.backend.app.foodbook.role.repository.RoleRepository;
import com.backend.app.foodbook.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private MealRepository mealRepository;

    @Override
    public void run(String... args) throws Exception {
        seedUsersAndRole();
    }

    public void seedUsersAndRole() throws NotFoundException {

        Role roleUser = seedRole("USER");
        Role roleVendor = seedRole("VENDOR");
        Role roleAdmin = seedRole("ADMIN");
        Meal meal1 = seedMeal(
                "Big Burger",
                "sandwich bread and beef",
                List.of(
                        "https://res.cloudinary.com/dmepvxtwv/image/upload/v1666764821/lr7aivqfbssjpzdia6nt.jpg",
                        "https://res.cloudinary.com/dmepvxtwv/image/upload/v1666764825/witbaux16mwgdftgurz2.jpg"
                ),
                5000
        );

        Meal meal2 = seedMeal(
                "Chicken Wings",
                "Grilled chickend with rice",
                List.of(
                        "https://res.cloudinary.com/dmepvxtwv/image/upload/v1666764821/lr7aivqfbssjpzdia6nt.jpg",
                        "https://res.cloudinary.com/dmepvxtwv/image/upload/v1666764825/witbaux16mwgdftgurz2.jpg"
                ),
                3000
        );
        User user1 = seedUser(
                "John",
                "Doe",
                "john@gmail.com",
                "#Password123",
                "0787857046",
                List.of(roleUser),
                null
        );

        User user2 = seedUser(
                "Sam",
                "Patrick",
                "patrick@gmail.com",
                "#Password123",
                "07878570346",
                List.of(roleUser, roleVendor),
                null
        );

        User user3 = seedUser(
                "Jane",
                "Angel",
                "angel@gmail.com",
                "#Password123",
                "0787383734",
                List.of(roleUser, roleVendor, roleAdmin),
                null
        );
        Business business1 = seedBusiness(
                "Meze Fresh",
                "Mexican Restaurant in Kigali",
                "mezefresh@gmail.com",
                "0787857043",
                List.of(meal1, meal2),
                user2
        );

        Business business2 = seedBusiness(
                "BWOK",
                "Italian Restaurant in Kigali",
                "bwokkigali@gmail.com",
                "0787857043",
                null,
                user2
        );

        user2.setBusinesses(List.of(business1, business2));
        userRepository.save(user2);
    }

    public Meal seedMeal(String name, String description, List<String> images, int price) {
        return mealRepository.save(
                new Meal(
                        null,
                        name,
                        description,
                        images,
                        price
                )
        );
    }

    public Business seedBusiness(String name, String description, String email, String contact, List<Meal> meals, User user) {
        return businessRepository.save(
                new Business(
                        null,
                        name,
                        description,
                        email,
                        contact,
                        meals,
                        null
                )
        );
    }

    public User seedUser(String firstName, String lastName, String email, String password, String contact, List<Role> roles, List<Business> businesses) {
        return userRepository.save(
                new User(
                        null,
                        firstName,
                        lastName,
                        email,
                        passwordEncoder.encode(password),
                        contact,
                        roles,
                        businesses
                )
        );
    }

    public Role seedRole(String name) {
        return roleRepository.save(
                new Role(
                        null,
                        name
                )
        );
    }
}

