package com.baeldung.resource.spring;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class JwtClaimsVerifier {

    private static final String ALLOWED_DOMAIN_NAME = "@baeldung.com";

    public void verify(Jwt jwt) {
        String preferred_username = jwt.getClaim("preferred_username");

        if (ObjectUtils.isEmpty(preferred_username) || hasInvalidDomain(preferred_username)) {
            throw new InvalidClaimException("preferred_username claim does not belong to valid domain");
        }
    }

    private static boolean hasInvalidDomain(String username) {
        return !username.toLowerCase().endsWith(ALLOWED_DOMAIN_NAME);
    }
}
