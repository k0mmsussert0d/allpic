package com.ewsie.allpic.image.model;

import com.ewsie.allpic.image.comment.model.Comment;
import com.ewsie.allpic.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "image")
@FieldDefaults(level = PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    Long id;

    @Column(name="path", nullable = false)
    String token;

    @Column(name="title", nullable = true)
    String title;

    @Column(name="upload_time", nullable = false)
    LocalDateTime uploadTime;

    @Column(name="public", nullable = false)
    Boolean isPublic;

    @Column(name="active", nullable = false)
    Boolean isActive;

    @JoinColumn(name="uploader_id", nullable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    User uploader;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    List<Comment> comments;

    public void addComment(Comment comment) {
        if (this.comments == null) {
            this.comments = new ArrayList<>();
        }

        this.comments.add(comment);
    }
}
