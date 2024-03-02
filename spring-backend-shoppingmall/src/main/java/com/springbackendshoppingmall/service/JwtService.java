package com.springbackendshoppingmall.service;

import io.jsonwebtoken.Claims;

public interface JwtService {
    String getTokens(String key, Object value);

    Claims getClaims(String token);

    boolean isValid(String token);

    int getId(String token);
}
