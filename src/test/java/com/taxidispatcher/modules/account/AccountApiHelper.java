package com.taxidispatcher.modules.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxidispatcher.ApiUrls;
import com.taxidispatcher.core.TestHelper;
import com.taxidispatcher.modules.account.adapter.web.dto.request.PasswordLoginRequest;
import com.taxidispatcher.modules.account.adapter.web.dto.request.RegisterBasicRequest;
import com.taxidispatcher.modules.account.adapter.web.dto.response.LoginResponse;
import com.taxidispatcher.modules.account.domain.model.IdentifierKind;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountApiHelper extends TestHelper {
    private final String loginId;
    private final String password;

    public AccountApiHelper(MockMvc mockMvc, ObjectMapper objectMapper, String loginId, String password) {
        super(mockMvc, objectMapper);
        this.loginId = loginId;
        this.password = password;
    }

    /** 어카운트 등록 */
    public String register() throws Exception {
        // 어카운트 등록
        RegisterBasicRequest registerBasicRequest = new RegisterBasicRequest(
                IdentifierKind.ID,
                loginId,
                password
        );

        String registerBasicBody = convertString(registerBasicRequest);

        var res = postJson(ApiUrls.Account.REGISTER, registerBasicBody)
                .andExpect(status().isCreated())
                .andReturn();

        return res.getResponse().getContentAsString();
    }

    /** 로그인 (권한 X) */
    public String login() throws Exception {
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

        return loginToken;
    }

    /** 로그인 (사용자 권한) */
    public String loginUser() throws Exception {
        // 로그인
        PasswordLoginRequest loginRequest = new PasswordLoginRequest(
                loginId, password
        );
        var loginRes = postJson(ApiUrls.Account.LOGIN_BASIC_USER, convertString(loginRequest))
                .andExpect(status().isOk())
                .andReturn();

        String loginToken = null;
        if (loginRes.getResponse().getStatus() >= 200 && loginRes.getResponse().getStatus() < 300) {
            loginToken = read(loginRes.getResponse().getContentAsString(), LoginResponse.class)
                    .token();
        }

        return loginToken;
    }

    /** 로그인 (택시 기사 권한) */
    public String loginDriver() throws Exception {

        // 로그인
        PasswordLoginRequest loginRequest = new PasswordLoginRequest(
                loginId, password
        );
        var loginRes = postJson(ApiUrls.Account.LOGIN_BASIC_DRIVER, convertString(loginRequest))
                .andExpect(status().isOk())
                .andReturn();

        String loginToken = null;
        if (loginRes.getResponse().getStatus() >= 200 && loginRes.getResponse().getStatus() < 300) {
            loginToken = read(loginRes.getResponse().getContentAsString(), LoginResponse.class)
                    .token();
        }

        return loginToken;
    }
}
