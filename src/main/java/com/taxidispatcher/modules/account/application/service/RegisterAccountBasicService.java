package com.taxidispatcher.modules.account.application.service;

import com.taxidispatcher.modules.account.application.port.in.RegisterAccountBasicCommand;
import com.taxidispatcher.modules.account.application.port.in.RegisterAccountBasicUseCase;
import com.taxidispatcher.modules.account.application.port.out.AccountRepository;
import com.taxidispatcher.modules.account.application.port.out.PasswordHasher;
import com.taxidispatcher.modules.account.domain.aggregate.Account;
import com.taxidispatcher.modules.account.domain.aggregate.BasicCredential;
import com.taxidispatcher.modules.account.domain.model.*;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterAccountBasicService implements RegisterAccountBasicUseCase {
    private final AccountRepository accountRepository;
    private final PasswordHasher passwordHasher;
    private Clock clock;

    @PostConstruct
    public void init() {
        this.clock = Clock.systemUTC();
    }

    @Override
    public AccountId handle(RegisterAccountBasicCommand registerAccountBasicCommand) {
        LoginIdentifier loginIdentifier = LoginIdentifier.of( // 로그인 수단 생성
                registerAccountBasicCommand.identifierKind(),
                registerAccountBasicCommand.loginId()
        );

        // 어카운트 중복 검사
        if (accountRepository.existsByIdentifier(loginIdentifier)) {
            throw new AppException(ErrorCode.CONFLICT, "이미 등록된 로그인 수단입니다.");
        }

        AccountId newId = AccountId.newId(); // 어카운트 아이디 발급
        HashPassword hashPassword = new HashPassword(passwordHasher.hash(registerAccountBasicCommand.rawPassword())); // 비밀번호 암호화
        BasicCredential basicCredential = BasicCredential.of( // 인증 수단 생성
                UUID.randomUUID(),
                newId,
                true,
                Instant.now(clock),
                null,
                loginIdentifier,
                hashPassword
        );

        Account newAccount = Account.of( // 어카운트 생성
                newId,
                AccountStatus.ACTIVE,
                Version.of(0l)
        );
        newAccount.addCredential(basicCredential); // 어카운트 인증 수단 추가

        // 어카운트 등록
        accountRepository.save(newAccount);

        return newId;
    }
}
