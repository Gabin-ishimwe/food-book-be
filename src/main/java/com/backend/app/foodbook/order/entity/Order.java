package com.backend.app.foodbook.order.entity;

import com.backend.app.foodbook.meal.entity.Meal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer_orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @SequenceGenerator(
            name = "order_id_sequence",
            sequenceName = "order_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "order_id_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "order_meal_mapping",
            joinColumns = @JoinColumn(
                    name = "order_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "meal_id",
                    referencedColumnName = "id"
            )
    )
    private List<Meal> meal;

    private Long amount;

    private boolean orderPlaced = false;
}
