package com.example.demo.entities;

import com.example.demo.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    @UuidGenerator
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private String email;

    private String firstname;

    private String lastname;

    private String password;

    private Role role;
}
