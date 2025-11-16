package org.example.userservice.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.example.userservice.common.domain.entities.BaseEntity;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity {
    private String name;
    private short age;
    private String email;

    public User(String name, short age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }
}
