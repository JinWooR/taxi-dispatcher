package com.taxidispatcher.shared.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

public record AccountPrincipal(UUID accountId, Actor actor, Collection<? extends GrantedAuthority> authorities) {
}
