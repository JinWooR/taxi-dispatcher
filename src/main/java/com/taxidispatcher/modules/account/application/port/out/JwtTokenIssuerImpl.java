package com.taxidispatcher.modules.account.application.port.out;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenIssuerImpl implements JwtTokenIssuer {
    private final Clock clock = Clock.systemUTC();

    @Override
    public String issue(Claims claims) {
        claimsValidate(claims);

        return Jwts.builder()
                .claims(claims)
                .compact();
    }

    private void claimsValidate(Claims claims) {
        if (claims.isEmpty()) {
            throw new IllegalArgumentException("Claims is empty!");
        }

        if (claims.getIssuer() == null || claims.getIssuer().isBlank()) {
            throw new IllegalArgumentException("Claims issuer is empty!");
        }

        if (claims.getIssuedAt() == null) {
            throw new IllegalArgumentException("Claims issuedAt is empty!");
        } else if (claims.getIssuedAt().after(new Date(Instant.now(clock).toEpochMilli()))) {
            throw new IllegalArgumentException("Claims issuedAt 값이 현재 시간보다 미래일 수 없습니다.");
        }

        if (claims.getExpiration() == null) {
            throw new IllegalArgumentException("Claims expiration is empty!");
        } else if (claims.getExpiration().before(claims.getIssuedAt())) {
            throw new IllegalArgumentException("Claims 만료일(expiration)이 발행일(issuedAt)보다 느릴 수 없습니다");
        }

        if (claims.getSubject() == null || claims.getSubject().isBlank()) {
            throw new IllegalArgumentException("Claims subject is empty!");
        }
    }
}
