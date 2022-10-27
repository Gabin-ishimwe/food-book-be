package com.backend.app.foodbook.meal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "meal")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meal {
    @Id
    @SequenceGenerator(
            name = "meal_id_sequence",
            sequenceName = "meal_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "meal_id_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    private String name;
    private String description;

    @ElementCollection
    private List<String> images;
    private int price;

    private Boolean onMenu = false;
}
