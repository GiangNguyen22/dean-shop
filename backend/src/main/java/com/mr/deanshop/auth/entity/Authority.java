package com.mr.deanshop.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Table(name = "AUTH_AUTHORITY")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority  implements GrantedAuthority {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String roleCode;

    private String roleDescription;


    @Override
    public String getAuthority() {
        return roleCode;
    }
}
