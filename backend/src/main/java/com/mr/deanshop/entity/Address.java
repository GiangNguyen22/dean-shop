package com.mr.deanshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mr.deanshop.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue
    private UUID id ;
    @Column(nullable = false)
    private String name;

    private String street;
    private String state;
    private String city;
    @Column(name = "zip_code")
    private String zipCode;
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private User user;
}
