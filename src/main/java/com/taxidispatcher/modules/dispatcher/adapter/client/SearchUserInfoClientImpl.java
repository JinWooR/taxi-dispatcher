package com.taxidispatcher.modules.dispatcher.adapter.client;

import com.taxidispatcher.modules.dispatcher.application.port.out.SearchUserInfoClient;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import com.taxidispatcher.shared.core.domain.user.dto.InternalUserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class SearchUserInfoClientImpl implements SearchUserInfoClient {
    private final String USER_BASE_URL;
    private final String USER_INFO;

    public SearchUserInfoClientImpl(
            @Value("${endPoints.user-bc.base-url}") String USER_BASE_URL,
            @Value("${endPoints.user-bc.internal-user-id}") String USER_INFO) {
        this.USER_BASE_URL = USER_BASE_URL;
        this.USER_INFO = USER_INFO;
    }

    @Override
    public InternalUserResponse handle(UUID userId) {
        var res = RestClient.create(USER_BASE_URL)
                .get()
                .uri(USER_INFO, userId.toString())
                .retrieve()
                .toEntity(InternalUserResponse.class);

        if (res.getStatusCode().is2xxSuccessful()) {
            return res.getBody();
        } else {
            throw new AppException(ErrorCode.VALIDATION, "USER-INFO 조회 실패");
        }
    }
}
