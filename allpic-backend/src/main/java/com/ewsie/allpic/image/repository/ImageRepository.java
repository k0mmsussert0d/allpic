package com.ewsie.allpic.image.repository;

import com.ewsie.allpic.image.model.Image;
import com.ewsie.allpic.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findById(Long id);

    Optional<List<Image>> findAllByUploader(User uploader);
}
