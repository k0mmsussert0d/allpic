package com.ewsie.allpic.user.role;

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
    @Column(name = "role_id", columnDefinition = "serial")
    int id;

    @Column(name = "role_name", unique = true)
    String roleName;
}
