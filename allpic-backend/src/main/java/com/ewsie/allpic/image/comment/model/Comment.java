package com.ewsie.allpic.image.comment.model;

import com.ewsie.allpic.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "comment")
@FieldDefaults(level = PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Long id;

    @Column(name = "message", length = 1024)
    String message;

    @Column(name = "add_time")
    LocalDateTime timeAdded;

    @Column(name = "public")
    Boolean isPublic;

    @JoinColumn(name = "author_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    User author;
}
