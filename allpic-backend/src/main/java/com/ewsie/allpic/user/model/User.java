package com.ewsie.allpic.user.model;

import com.ewsie.allpic.user.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "users")
@FieldDefaults(level = PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Long id;

    @Column(name="username", length = 50, nullable = false, unique = true)
    String username;

    @Column(name="password", nullable = false)
    String password;

    @Column(name="email", nullable = false)
    String email;

    @Column(name="register_time", nullable = false)
    LocalDateTime registerTime;

    @Column(name="active", nullable = false)
    Boolean isActive;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    Role role;
}
