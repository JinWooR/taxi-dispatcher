package com.taxidispatcher.modules.dispatch;

import com.taxidispatcher.ApiUrls;
import com.taxidispatcher.TestBaseClient;
import com.taxidispatcher.modules.account.adapter.web.dto.request.PasswordLoginRequest;
import com.taxidispatcher.modules.account.adapter.web.dto.request.RegisterBasicRequest;
import com.taxidispatcher.modules.account.adapter.web.dto.response.LoginResponse;
import com.taxidispatcher.modules.account.domain.model.IdentifierKind;
import com.taxidispatcher.modules.dispatcher.adapter.web.dto.request.WriteDispatchRequest;
import com.taxidispatcher.modules.user.adapter.web.dto.request.RegisterUserRequest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DispatchApiTest extends TestBaseClient {

    @Test
    void fullTest() throws Exception {
        String bearer = userLogin();

        var startAddr = new WriteDispatchRequest.AddressGeo("ex)출발지", 37.5705d, 126.9774d);
        var arrivalAddr = new WriteDispatchRequest.AddressGeo("ex)목적지", 37.5490d, 127.0812d);
        var req = new WriteDispatchRequest(startAddr, arrivalAddr);

        var res = postJson(ApiUrls.Dispatch.User.WRITE, convertString(req), bearer)
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        System.out.println("사용자) 배차 요청\n"
            + res.getResponse().getContentAsString());
    }

    private final String loginId = "tester_001";
    private final String password = "test012!";

    private String userLogin() throws Exception {
        // 어카운트 등록 및 로그인(+토큰 발급)
        String loginToken = "Bearer " + accountApi();

        // 사용자 등록
        RegisterUserRequest userRequest = new RegisterUserRequest("테스터", "서울 강남구 가로수길 9", "13층");
        var registerRes = postJson(ApiUrls.User.REGISTER, convertString(userRequest), loginToken)
                .andExpect(status().isCreated())
                .andReturn();

        System.out.println("사용자 등록 Response\n"
                + registerRes.getResponse().getContentAsString());

        // 사용자 권한 로그인(+토큰 발급)
        return "Bearer " + accountApi_User();
    }

    private String accountApi() throws Exception {
        // 어카운트 등록
        RegisterBasicRequest registerBasicRequest = new RegisterBasicRequest(
                IdentifierKind.ID,
                loginId,
                password
        );

        postJson(ApiUrls.Account.REGISTER, convertString(registerBasicRequest))
                .andExpect(status().isCreated());

        // 로그인
        PasswordLoginRequest loginRequest = new PasswordLoginRequest(
                loginId, password
        );
        var loginRes = postJson(ApiUrls.Account.LOGIN_BASIC, convertString(loginRequest))
                .andExpect(status().isOk())
                .andReturn();

        String loginToken = null;
        if (loginRes.getResponse().getStatus() >= 200 && loginRes.getResponse().getStatus() < 300) {
            loginToken = read(loginRes.getResponse().getContentAsString(), LoginResponse.class)
                    .token();
        }

        return loginToken;
    }

    private String accountApi_User() throws Exception {
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
}
