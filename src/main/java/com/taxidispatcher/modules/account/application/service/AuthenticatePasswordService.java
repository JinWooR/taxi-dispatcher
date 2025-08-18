package com.taxidispatcher.modules.account.application.service;

import com.taxidispatcher.modules.account.application.port.in.AuthenticatePasswordCommand;
import com.taxidispatcher.modules.account.application.port.in.AuthenticatePasswordUseCase;
import com.taxidispatcher.modules.account.application.port.out.AccountRepository;
import com.taxidispatcher.modules.account.application.port.out.JwtTokenIssuer;
import com.taxidispatcher.modules.account.application.port.out.PasswordHasher;
import com.taxidispatcher.modules.account.domain.aggregate.Account;
import com.taxidispatcher.modules.account.domain.aggregate.BasicCredential;
import com.taxidispatcher.modules.account.domain.model.IdentifierKind;
import com.taxidispatcher.modules.account.domain.model.LoginIdentifier;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticatePasswordService implements AuthenticatePasswordUseCase {
    private final AccountRepository accountRepository;
    private final PasswordHasher passwordHasher;
    private final JwtTokenIssuer jwtTokenIssuer;
    private Clock clock;

    @PostConstruct
    public void init() {
        this.clock = Clock.systemUTC();
    }

    @Override
    public String handle(AuthenticatePasswordCommand authenticatePasswordCommand) {
        LoginIdentifier loginIdentifier = LoginIdentifier.of(IdentifierKind.ID, authenticatePasswordCommand.loginId());
        
        // 어카운트 조회
        Account account = accountRepository.findByIdentifier(loginIdentifier)
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 잘못 입력되었습니다."));

        // 인증 수단 검증
        BasicCredential basicCredential = (BasicCredential) account.credentialsView().stream()
                .filter(c -> {
                    if (c instanceof BasicCredential bc) {
                        return bc.getLoginIdentifier().identifierKind() == IdentifierKind.ID;
                    }
                    return false;
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 잘못 입력되었습니다."));

        // 비밀번호 검사
        if (!passwordHasher.matches(basicCredential.getHashPassword().hashPassword(), authenticatePasswordCommand.rawPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 잘못 입력되었습니다.");
        }

        // 최근 사용 시간 최신화
        basicCredential.recentLastUse();
        accountRepository.save(account);

        // 토큰 발급
        Instant now = Instant.now(clock),
                expiration = now.plus(30, ChronoUnit.DAYS);
        String token = jwtTokenIssuer.issue(
                Jwts.claims()
                        .issuer("http://localhost:8080")
                        .issuedAt(new Date(now.toEpochMilli()))
                        .expiration(new Date(expiration.toEpochMilli()))
                        .subject(account.getAccountId().value().toString())
                        .add("login_identifier_kind", basicCredential.getLoginIdentifier().identifierKind())
                        .add("credential_id", basicCredential.getId())
                        .build()
        );

        return token;
    }
}
