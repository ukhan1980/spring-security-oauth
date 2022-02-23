package com.baeldung.resource.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

@ExtendWith(MockitoExtension.class)
class JwtRoleConverterTest {

    private static final String SCOPE = "scope";
    private static final String PREFERRED_USERNAME = "preferred_username";
    private static final SimpleGrantedAuthority READ_AUTHORITY = new SimpleGrantedAuthority("SCOPE_read");
    private static final SimpleGrantedAuthority WRITE_AUTHORITY = new SimpleGrantedAuthority("SCOPE_write");
    private static final SimpleGrantedAuthority SUPERUSER_AUTHORITY = new SimpleGrantedAuthority("SCOPE_superuser");

    private JwtRoleConverter jwtRoleConverter;

    @Mock
    private Jwt jwt;

    @BeforeEach
    void setup() {
        jwtRoleConverter = new JwtRoleConverter();
    }

    @Test
    void givenUserWithValidDomain_shouldAddSuperuserRole() {
        when(jwt.getClaimAsString(PREFERRED_USERNAME)).thenReturn("test@baeldung.com");
        when(jwt.getClaimAsString(SCOPE)).thenReturn("read write");

        Collection<GrantedAuthority> authorities = jwtRoleConverter.convert(jwt);

        assertThat(authorities).containsExactlyInAnyOrder(
            READ_AUTHORITY, WRITE_AUTHORITY, SUPERUSER_AUTHORITY
        );
    }

    @Test
    void givenUserWithInvalidDomain_shouldNotAddSuperuserRole() {
        when(jwt.getClaimAsString(PREFERRED_USERNAME)).thenReturn("test@test.com");
        when(jwt.getClaimAsString(SCOPE)).thenReturn("read write");

        Collection<GrantedAuthority> authorities = jwtRoleConverter.convert(jwt);

        assertThat(authorities).containsExactlyInAnyOrder(READ_AUTHORITY, WRITE_AUTHORITY);
    }

}
