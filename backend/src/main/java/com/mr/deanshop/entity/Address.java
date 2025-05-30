package com.mr.deanshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mr.deanshop.auth.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue
    private UUID id ;
    @Column(nullable = false)
    private String name;

    private String street;
    private String city;
    @Column(name = "zip_code")
    private String zipcode;
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private User user;
}
