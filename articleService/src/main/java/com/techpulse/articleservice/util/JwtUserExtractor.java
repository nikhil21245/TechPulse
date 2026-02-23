package com.techpulse.articleservice.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class JwtUserExtractor {

    /**
     * Gateway should forward X-User-Id header with authenticated user id.
     * This helper safely extracts it.
     */
    public Long extractUserId(HttpServletRequest request) {
        String header = request.getHeader("X-User-Id");
        if (header == null || header.isBlank()) return null;
        try {
            return Long.parseLong(header);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
