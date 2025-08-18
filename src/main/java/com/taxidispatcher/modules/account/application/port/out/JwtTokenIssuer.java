package com.taxidispatcher.modules.account.application.port.out;

import io.jsonwebtoken.Claims;

public interface JwtTokenIssuer {
    String issue(Claims claims);
}
