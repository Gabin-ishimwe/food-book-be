package com.backend.app.foodbook.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private int businessContact;
}
