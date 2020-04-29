package com.ewsie.allpic.session.model;

import com.ewsie.allpic.user.model.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "session")
@FieldDefaults(level = PRIVATE)
@Data
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Long id;

    @Column(name = "sesion_ident")
    String sessionIdentifier;

    @Column(name = "valid_until")
    LocalDateTime validUntil;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;
}
