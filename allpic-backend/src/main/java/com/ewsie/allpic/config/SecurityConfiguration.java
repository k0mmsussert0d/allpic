package com.ewsie.allpic.config;

import com.ewsie.allpic.user.security.AuthCookieFilter;
import com.ewsie.allpic.user.security.CustomLogoutSuccessHandler;
import com.ewsie.allpic.user.service.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

import java.util.HashMap;
import java.util.Map;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userDetailsService;

    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    private final AuthCookieFilter authCookieFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() {
        return authentication -> {
            throw new AuthenticationServiceException("Cannot authenticate " + authentication);
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf().disable()
                .logout(configurer -> {
                    configurer.addLogoutHandler(new HeaderWriterLogoutHandler(
                    new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.ALL)
                    ));
                    configurer.logoutSuccessHandler(customLogoutSuccessHandler);
                    configurer.deleteCookies("authentication");
                })
                .exceptionHandling(configurer -> configurer.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .addFilterAfter(authCookieFilter, SecurityContextPersistenceFilter.class)
                .authorizeRequests()
                .antMatchers("/auth/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
        ;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        String defaultEncodingId = "argon2";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(defaultEncodingId, new Argon2PasswordEncoder(16, 32, 8, 1 << 16, 4));
        return new DelegatingPasswordEncoder(defaultEncodingId, encoders);
    }
}
