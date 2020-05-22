package com.ewsie.allpic.image.service;

import com.ewsie.allpic.config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class ImageTokenService {

    private final AppConfig appConfig;
    private final SecureRandom random = new SecureRandom();

    public String generateToken(int length) {
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(appConfig.getImgTokenChars().charAt(
                    random.nextInt(appConfig.getImgTokenChars().length())
            ));
        }

        return sb.toString();
    }
}
