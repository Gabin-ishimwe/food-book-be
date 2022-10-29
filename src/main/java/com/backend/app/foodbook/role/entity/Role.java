package com.backend.app.foodbook.role.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
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
    @SequenceGenerator(
            sequenceName = "role_id_sequence",
            name = "role_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "role_id_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    @NotBlank(
            message = "Role name is required"
    )
    @Column(
            name = "role_name"
    )
    private String name;

//    @ManyToMany
//    @JoinTable(
//            name = "role_user_mapping",
//            joinColumns = @JoinColumn(
//                    name = "role_id",
//                    referencedColumnName = "id"
//            ),
//            inverseJoinColumns = @JoinColumn(
//                    name = "user_id",
//                    referencedColumnName = "id"
//            )
//    )
//    private List<User> user;
}
