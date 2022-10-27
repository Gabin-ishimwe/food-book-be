package com.backend.app.foodbook.Menu.entity;

import com.backend.app.foodbook.meal.entity.Meal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    @Id
    @SequenceGenerator(
            name = "meal_id_sequence",
            sequenceName = "meal_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "meal_id_sequence"
    )
    private Long id;

    @Column(
            name = "stock_number"
    )
    private Long stockNumber;

    @OneToOne
    @JoinColumn(
            name = "menu_id",
            referencedColumnName = "id"
    )
    private Meal meal;
}
