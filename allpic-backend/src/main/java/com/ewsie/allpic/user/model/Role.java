package com.ewsie.allpic.user.model;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "role")
@FieldDefaults(level = PRIVATE)
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    int roleId;

    @Column(name = "role")
    String role;
}
