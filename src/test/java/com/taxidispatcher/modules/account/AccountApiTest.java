package com.taxidispatcher.modules.account;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.taxidispatcher.ApiUrls;
import com.taxidispatcher.TestBaseClient;
import com.taxidispatcher.modules.account.adapter.web.dto.request.PasswordLoginRequest;
import com.taxidispatcher.modules.account.adapter.web.dto.request.RegisterBasicRequest;
import com.taxidispatcher.modules.account.adapter.web.dto.response.LoginResponse;
import com.taxidispatcher.modules.account.domain.model.IdentifierKind;
import org.junit.jupiter.api.Test;

class AccountApiTest extends TestBaseClient {
    private final String loginId = "tester_001";
    private final String password = "test012!";

    @Test
    void fullTest() throws Exception {
        // 어카운트 등록
        RegisterBasicRequest registerBasicRequest = new RegisterBasicRequest(
                IdentifierKind.ID,
                loginId,
                password
        );

        String registerBasicBody = convertString(registerBasicRequest);

        postJson(ApiUrls.Account.REGISTER, registerBasicBody)
                .andExpect(status().isCreated());

        // 로그인
        PasswordLoginRequest loginRequest = new PasswordLoginRequest(
                loginId, password
        );
        String loginRequestBody = convertString(loginRequest);

        var loginRes = postJson(ApiUrls.Account.LOGIN_BASIC, loginRequestBody)
                .andExpect(status().isOk())
                .andReturn();

        String loginToken = null;
        if (loginRes.getResponse().getStatus() >= 200 && loginRes.getResponse().getStatus() < 300) {
            loginToken = read(loginRes.getResponse().getContentAsString(), LoginResponse.class)
                    .token();
        }
    }
}
