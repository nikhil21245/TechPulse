package com.techpulse.user_service.oauth;

import com.techpulse.user_service.entity.Role;
import com.techpulse.user_service.entity.User;
import com.techpulse.user_service.jwt.JwtService;
import com.techpulse.user_service.repository.UserRepository;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = User.builder()
                    .name(name)
                    .email(email)
                    .role(Role.USER)
                    .interests(new HashSet<>())
                    .build();
            return userRepository.save(newUser);
        });

        // ✅ Build temporary UserDetails for JWT
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(email)
                .password("") // OAuth users typically don’t have passwords
                .roles(user.getRole().name())
                .build();

        // ✅ Generate JWT token safely
        String token = jwtService.generateToken(userDetails);

        // ✅ Redirect to frontend with token
        response.sendRedirect("http://localhost:3000/auth/success?token=" + token);
    }
}
