package com.ewsie.allpic.image.model;

import com.ewsie.allpic.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import java.time.LocalDateTime;

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

    @Column(name="active", nullable = false)
    Boolean isActive;

    @JoinColumn(name="uploader_id", nullable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    User uploader;
}
