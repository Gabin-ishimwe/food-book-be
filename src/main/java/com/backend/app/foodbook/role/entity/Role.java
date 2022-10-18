package com.backend.app.foodbook.role.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "roles",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_role_name",
                        columnNames = "role_name"
                )
        }
)
public class Role {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(
            message = "Role name is required"
    )
    @Column(
            name = "role_name"
    )
    private String name;
}
