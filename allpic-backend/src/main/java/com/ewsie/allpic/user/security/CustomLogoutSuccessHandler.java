package com.ewsie.allpic.user.security;

import com.ewsie.allpic.user.session.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final SessionService sessionService;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {

        Optional<String> sessionId = Optional.ofNullable(AuthCookieFilter.extractAuthCookie(httpServletRequest));
        if (sessionId.isPresent()) {
            sessionService.removeByIdentifier(sessionId.get());
        }

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.getWriter().flush();
    }

}
