package com.backend.app.foodbook.business.entity;

import com.backend.app.foodbook.auth.entity.User;
import com.backend.app.foodbook.meal.entity.Meal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "business")
public class Business {
    @Id
    @SequenceGenerator(
            name = "business_id_sequence",
            sequenceName = "business_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "business_id_sequence"
    )
    private Long id;

    @Column(
            name = "business_name"
    )
    private String businessName;

    @Column(
            name = "business_description"
    )
    private String businessDescription;

    @Column(
            name = "business_email"
    )
    private String businessEmail;

    @Column(
            name = "business_contact"
    )
    private String businessContact;

    @OneToMany(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "business_id",
            referencedColumnName = "id"
    )
    private List<Meal> meals;

    //    @JsonIgnoreProperties({"businesses"})
    @JsonIgnore
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    private User user;
}
