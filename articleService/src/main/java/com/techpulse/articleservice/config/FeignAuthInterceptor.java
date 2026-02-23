package com.techpulse.articleservice.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FeignAuthInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // Option A: Use a service account token
        String serviceToken = "Bearer YOUR_SERVICE_ACCOUNT_TOKEN";
        template.header("Authorization", serviceToken);

        // Option B: Pass through user token from context
        // String userToken = SecurityContextHolder.getContext().getAuthentication()...
        // template.header("Authorization", "Bearer " + userToken);

        log.debug("Added Authorization header to Feign request");
    }
}