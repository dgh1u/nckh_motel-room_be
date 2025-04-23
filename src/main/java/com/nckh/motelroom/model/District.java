package com.nckh.motelroom.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "district")
public class District {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;



    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "district",
            orphanRemoval = true)
    @JsonManagedReference
    private Collection<Accomodation> accomodations;
}