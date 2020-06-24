package com.ewsie.allpic.user.security;

import com.ewsie.allpic.config.AppConfig;
import com.ewsie.allpic.user.security.UserAuthentication;
import com.ewsie.allpic.user.session.model.SessionDTO;
import com.ewsie.allpic.user.session.service.SessionDTOService;
import com.ewsie.allpic.user.model.CustomUserDetails;
import com.ewsie.allpic.user.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AuthCookieFilter extends GenericFilterBean {

    private final SessionDTOService sessionDTOService;
    private final AppConfig appConfig;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        Optional<String> sessionId = Optional.ofNullable(extractAuthCookie((HttpServletRequest) servletRequest));

        if (sessionId.isPresent()) {
            Optional<SessionDTO> sessionDTO = Optional.ofNullable(sessionDTOService.findByIdentifier(sessionId.get()));

            if (sessionDTO.isPresent()) {
                UserDTO userDTO = sessionDTO.get().getUser();
                CustomUserDetails customUserDetails = new CustomUserDetails(userDTO);

                SecurityContextHolder.getContext().setAuthentication(new UserAuthentication(customUserDetails));
            }
        }

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", appConfig.getCorsHosts());
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Origin, Authorization, Content-Type, Cache-Control");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(servletRequest, response);
    }

    public static String extractAuthCookie(HttpServletRequest request) {
        List<Cookie> cookies = Arrays.asList(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]));

        if (!cookies.isEmpty()) {
            Optional<Cookie> authCookie = cookies.stream()
                    .filter(cookie -> cookie.getName().equals("authentication"))
                    .findFirst();

            if (authCookie.isPresent()) {
                return authCookie.get().getValue();
            }
        }

        return null;
    }
}
