package com.backend.app.foodbook.auth.entity;

import com.backend.app.foodbook.business.entity.Business;
import com.backend.app.foodbook.role.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_id_sequence",
            sequenceName = "user_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_sequence"
    )
    private Long id;

    @Column(
            name = "first_name"
    )
    private String firstName;

    @Column(
            name = "last_name"
    )
    private String lastName;

    @Column(
            name = "email"
    )
    private String email;

    @Column(
            name = "password"
    )
    private String password;

    @Column(
            name = "contact_number"
    )
    private String contactNumber;

    @OneToMany
    @JoinColumn(
            name = "role_id",
            referencedColumnName = "id"
    )
   private List<Role> roles;


    @OneToMany
    @JoinColumn(
            name = "business_ids",
            referencedColumnName = "id"
    )
    private List<Business> businesses;
}
