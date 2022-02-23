package com.baeldung.resource.spring;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        List<GrantedAuthority> scopes = Arrays.stream(jwt.getClaimAsString("scope").split(" "))
            .map(scope -> new SimpleGrantedAuthority("SCOPE_" + scope))
            .collect(Collectors.toList());

        String domainName = jwt.getClaimAsString("preferred_username");
        if (StringUtils.endsWithIgnoreCase(domainName, "@baeldung.com")) {
             scopes.add(new SimpleGrantedAuthority("SCOPE_superuser"));
        }

        return scopes;
    }
}

