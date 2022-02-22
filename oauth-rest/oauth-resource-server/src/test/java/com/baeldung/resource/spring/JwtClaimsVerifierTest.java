package com.baeldung.resource.spring;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;

@ExtendWith(MockitoExtension.class)
class JwtClaimsVerifierTest {

    private static final String PREFERRED_USERNAME = "preferred_username";

    private JwtClaimsVerifier jwtClaimsVerifier;

    @Mock
    private Jwt jwt;

    @BeforeEach
    void setup() {
        jwtClaimsVerifier = new JwtClaimsVerifier();
    }

    @Test
    public void givenAClaimWithPreferredUserName_whenItContainsValidDomainName_thenOk() {
        when(jwt.getClaim(PREFERRED_USERNAME)).thenReturn("test@baeldung.com");

        assertThatNoException()
            .isThrownBy(() -> jwtClaimsVerifier.verify(jwt));
    }

    @Test
    public void givenAClaimWithPreferredUserName_whenItContainsInvalidDomainName_thenException() {
        when(jwt.getClaim(PREFERRED_USERNAME)).thenReturn("test@test.com");

        assertThatExceptionOfType(InvalidClaimException.class)
            .isThrownBy(() -> jwtClaimsVerifier.verify(jwt));
    }

    @Test
    public void givenAClaimWithoutPreferredUserName_thenException() {
        when(jwt.getClaim(PREFERRED_USERNAME)).thenReturn(null);

        assertThatExceptionOfType(InvalidClaimException.class)
            .isThrownBy(() -> jwtClaimsVerifier.verify(jwt));
    }

    @Test
    public void givenAClaimWithEmptyPreferredUserName_thenException() {
        when(jwt.getClaim(PREFERRED_USERNAME)).thenReturn("");

        assertThatExceptionOfType(InvalidClaimException.class)
            .isThrownBy(() -> jwtClaimsVerifier.verify(jwt));
    }
}
