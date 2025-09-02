package com.taxidispatcher.modules.account.adapter.client;

import com.taxidispatcher.modules.account.adapter.client.dto.ActorDTO;
import com.taxidispatcher.modules.account.adapter.client.dto.DriverAccountDTO;
import com.taxidispatcher.modules.account.adapter.client.dto.UserAccountDTO;
import com.taxidispatcher.modules.account.application.port.out.RoleCheckerClient;
import com.taxidispatcher.modules.account.domain.model.AccountId;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RoleCheckerClientImpl implements RoleCheckerClient {
    private final String USER_BASE_URL;
    private final String USER_ACCOUNT_INFO;
    private final String DRIVER_BASE_URL;
    private final String DRIVER_ACCOUNT_INFO;

    public RoleCheckerClientImpl(
            @Value("${endPoints.user-bc.base-url}") String USER_BASE_URL,
            @Value("${endPoints.user-bc.internal-user}") String USER_ACCOUNT_INFO,
            @Value("${endPoints.driver-bc.base-url}") String DRIVER_BASE_URL,
            @Value("${endPoints.driver-bc.internal-driver}") String DRIVER_ACCOUNT_INFO
    ) {
        this.USER_BASE_URL = USER_BASE_URL;
        this.USER_ACCOUNT_INFO = USER_ACCOUNT_INFO;
        this.DRIVER_BASE_URL = DRIVER_BASE_URL;
        this.DRIVER_ACCOUNT_INFO = DRIVER_ACCOUNT_INFO;
    }

    @Override
    public ActorDTO callUser(AccountId accountId) {
        var res = RestClient.create(USER_BASE_URL)
                .get()
                .uri(USER_ACCOUNT_INFO, accountId.value())
                .retrieve()
                .toEntity(UserAccountDTO.class);

        if (res.getStatusCode().is2xxSuccessful() && res.getBody() != null) {
            return new ActorDTO("USER", res.getBody().userId());
        } else {
            throw new AppException(ErrorCode.VALIDATION, "USER 정보 조회 실패");
        }
    }

    @Override
    public ActorDTO callDriver(AccountId accountId) {
        var res = RestClient.create(DRIVER_BASE_URL)
                .get()
                .uri(DRIVER_ACCOUNT_INFO, accountId.value())
                .retrieve()
                .toEntity(DriverAccountDTO.class);

        if (res.getStatusCode().is2xxSuccessful() && res.getBody() != null) {
            return new ActorDTO("DRIVER", res.getBody().driverId());
        } else {
            throw new AppException(ErrorCode.VALIDATION, "DRIVER 정보 조회 실패");
        }
    }
}
