package com.ewsie.allpic.user.session;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@RequiredArgsConstructor
@Service
public class SessionTokenService {

    private final SecureRandom random = new SecureRandom();

    private static final long RESEED = 100000L;

    public String createToken() {
        long r0 = random.nextLong();

        if (r0 % RESEED == 1L) {
            random.setSeed(random.generateSeed(8));
        }

        long r1 = random.nextLong();
        return Long.toString(r0, 36) + Long.toString(r1, 36);
    }
}

