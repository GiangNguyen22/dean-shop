package com.mr.deanshop.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Table(name = "AUTH_USER_DETAILS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User  implements UserDetails {
    @Id
    @GeneratedValue
   private UUID id;
   private String name;
   @Column(nullable = false, unique = true)
   private String email;

   @JsonIgnore
   private String password;
   private String phone;

   @Column(name = "create_on")
   @CreationTimestamp
   private Date createOn;
   private Date updateOn;
   private String verificationCode;
   private boolean enabled = false;

   @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
   @JoinTable(name = "AUTH_USER_AUTHORITY", joinColumns = @JoinColumn(referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(referencedColumnName = "id"))
   private List<Authority> authorities;

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return List.of();
   }

   @PreUpdate
   protected void onUpdate() {
      updateOn = new Date(); // hoặc dùng Instant.now() nếu dùng java.time
   }

   @Override
   public String getUsername() {
      return this.email;
   }

   @Override
   public String getPassword() {
      return this.password;
   }
}
