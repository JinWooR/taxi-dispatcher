package com.taxidispatcher.modules.account.application.port.out;

import com.taxidispatcher.modules.account.adapter.client.dto.ActorDTO;
import com.taxidispatcher.modules.account.domain.model.AccountId;

public interface RoleCheckerClient {
    ActorDTO callUser(AccountId accountId);
    ActorDTO callDriver(AccountId accountId);
}
