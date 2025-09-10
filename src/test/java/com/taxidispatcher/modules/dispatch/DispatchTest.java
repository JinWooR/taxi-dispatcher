package com.taxidispatcher.modules.dispatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxidispatcher.core.TestBase;
import com.taxidispatcher.modules.account.AccountApiHelper;
import com.taxidispatcher.modules.dispatcher.adapter.web.dto.request.WriteDispatchRequest;
import com.taxidispatcher.modules.user.UserApiHelper;
import com.taxidispatcher.modules.user.adapter.web.dto.request.RegisterUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class DispatchTest extends TestBase {
    private final AccountApiHelper accountApiHelper;
    private final UserApiHelper userApiHelper;
    private final DispatchApiHelper dispatchApiHelper;

    public DispatchTest(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
        this.accountApiHelper = new AccountApiHelper(mockMvc, objectMapper, "test001", "test012!");
        this.userApiHelper = new UserApiHelper(mockMvc, objectMapper);
        this.dispatchApiHelper = new DispatchApiHelper(mockMvc, objectMapper);
    }

    @Test
    void fullTest() throws Exception {
        accountApiHelper.register();
        String token = "Bearer " + accountApiHelper.login();

        userApiHelper.setToken(token);
        userApiHelper.register(new RegisterUserRequest("테스터", "서울 강남구 가로수길 9", "13층"));

        token = "Bearer " + accountApiHelper.loginUser();

        dispatchApiHelper.setToken(token);
        var dispatch = dispatchApiHelper.write(
                new WriteDispatchRequest(
                        new WriteDispatchRequest.AddressGeo("ex)출발지", 37.5705d, 126.9774d),
                        new WriteDispatchRequest.AddressGeo("ex)목적지", 37.5490d, 127.0812d)
                )
        );

        dispatchApiHelper.info(dispatch.get().dispatchId());

        // 배차 취소
        dispatchApiHelper.cancel(dispatch.get().dispatchId().toString());
    }
}
