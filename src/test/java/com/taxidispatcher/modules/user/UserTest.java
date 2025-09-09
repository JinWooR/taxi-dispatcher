package com.taxidispatcher.modules.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxidispatcher.core.TestBase;
import com.taxidispatcher.modules.account.AccountApiHelper;
import com.taxidispatcher.modules.user.adapter.web.dto.request.RegisterUserRequest;
import com.taxidispatcher.modules.user.adapter.web.dto.request.UpdateUserAddressRequest;
import com.taxidispatcher.modules.user.adapter.web.dto.request.UpdateUserNameRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class UserTest extends TestBase {
    private final AccountApiHelper accountApiHelper;
    private final UserApiHelper userApiHelper;

    public UserTest(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
        this.accountApiHelper = new AccountApiHelper(mockMvc, objectMapper, "test001", "tester012!");
        this.userApiHelper = new UserApiHelper(mockMvc, objectMapper);
    }

    @Test
    void fullTest() throws Exception {
        accountApiHelper.register();
        String token = "Bearer " + accountApiHelper.login();

        userApiHelper.setToken(token);
        userApiHelper.register(new RegisterUserRequest("테스터", "서울 강남구 가로수길 9", "13층"));

        token = "Bearer " + accountApiHelper.loginUser();

        userApiHelper.setToken(token);
        userApiHelper.updateName(new UpdateUserNameRequest("테스터(이름수정)"));
        userApiHelper.updateAddress(new UpdateUserAddressRequest("서울 송파구 송파대로 558", "월드타워 57층"));

        userApiHelper.delete();
    }
}
