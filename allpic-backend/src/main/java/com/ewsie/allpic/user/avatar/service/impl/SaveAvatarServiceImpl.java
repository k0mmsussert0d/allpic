package com.ewsie.allpic.user.avatar.service.impl;

import com.ewsie.allpic.image.service.ImageThumbnailService;
import com.ewsie.allpic.image.service.UploadFileService;
import com.ewsie.allpic.user.avatar.service.SaveAvatarService;
import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.service.UserDTOService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SaveAvatarServiceImpl implements SaveAvatarService {

    private final UserDTOService userDTOService;
    private final ImageThumbnailService imageThumbnailService;
    private final UploadFileService uploadFileService;

    @Override
    public void saveAvatar(InputStream avatarFileInputStream, UserDTO user) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        avatarFileInputStream.transferTo(baos);
        InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
        File cropped = imageThumbnailService.generateThumbnail(inputStream);

        Map<String, String> objectMetadata = Map.of("type", "avatar");
        uploadFileService.uploadFile(new FileInputStream(cropped), user.getUsername() + "_avatar", objectMetadata, "image/jpeg");

        user.setHasAvatar(true);
        userDTOService.create(user);
    }
}
