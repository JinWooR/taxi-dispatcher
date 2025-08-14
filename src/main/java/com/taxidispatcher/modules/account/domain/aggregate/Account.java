package com.taxidispatcher.modules.account.domain.aggregate;

import com.taxidispatcher.modules.account.domain.model.AccountId;
import com.taxidispatcher.modules.account.domain.model.AccountStatus;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account {
    @Getter
    private final AccountId accountId;
    @Getter
    AccountStatus status;
    private final List<Credential> credentials;

    private Account(AccountId accountId, AccountStatus status, List<Credential> credentials) {
        this.accountId = accountId;
        this.status = status;
        this.credentials = credentials;
    }

    public static Account of(AccountId accountId, AccountStatus status, List<Credential> credentials) {
        return new Account(accountId, status, credentials);
    }

    public static Account of(AccountId accountId, AccountStatus status) {
        return new Account(accountId, status, new ArrayList<>());
    }

    // 읽기 전용
    public List<Credential> credentialsView() {
        return Collections.unmodifiableList(credentials);
    }

    // 인증 수단 추가
    public void addCredential(Credential newCredential) {
        boolean duplicated = false;

        if (newCredential instanceof BasicCredential nbc) {
            duplicated = this.credentials.stream()
                    .anyMatch(c -> c instanceof  BasicCredential bc
                            && bc.getLoginIdentifier().getIdentifierKind() == nbc.getLoginIdentifier().getIdentifierKind());
        } else if (newCredential instanceof OAuthCredential noc) {
            duplicated = this.credentials.stream()
                    .anyMatch(c -> c instanceof OAuthCredential oc
                            && oc.getoAuthKind() == noc.getoAuthKind());
        } else {
            throw new IllegalStateException("지원하지 않는 Credentail : " + newCredential.getClass().getName());
        }

        if (duplicated) {
            throw new IllegalStateException("이미 동일한 로그인 인증 수단을 보유하고 있습니다.");
        }

        this.credentials.add(newCredential);
    }

    // 계정 상태 변경
    public void transitionStatus(AccountStatus nextStatus) {
        if (this.status != null) {
            this.status.ensureTransition(nextStatus);
        }

        this.status = nextStatus;
    }
}
