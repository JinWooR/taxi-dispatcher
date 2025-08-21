package com.taxidispatcher.shared.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SecurityJwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String accountId = Optional.ofNullable(jwt.getClaimAsString("account_id"))
                .orElse(jwt.getSubject());

        // 권한 수집
        var authorities = toAuthorities(jwt.getClaim("roles"));

        // 액터 수집
        var actor = toActor(jwt.getClaimAsMap("actor"));

        var principal = new AccountPrincipal(UUID.fromString(accountId), actor, authorities);

        return new UsernamePasswordAuthenticationToken(principal, jwt, authorities);
    }

    private Actor toActor(Map<String, Object> actor) {
        return Optional.ofNullable(actor)
                .map(m -> new Actor((String) m.get("type"), (String) m.get("id")))
                .orElse(null);
    }

    private Set<GrantedAuthority> toAuthorities(Object roles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (roles instanceof String s) {
            for (String role : s.split("[\\s,]+")) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        } else if (roles instanceof Collection<?> c) {
            c.forEach(o -> authorities.add(new SimpleGrantedAuthority(o.toString())));
        }

        return authorities;
    }
}
