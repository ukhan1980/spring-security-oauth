package com.baeldung.resource.spring;

import org.springframework.security.core.AuthenticationException;

public class InvalidClaimException extends AuthenticationException {

    public InvalidClaimException(String msg) {
        super(msg);
    }
}
