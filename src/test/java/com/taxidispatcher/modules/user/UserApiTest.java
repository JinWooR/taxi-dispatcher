package com.taxidispatcher.modules.user;

import com.taxidispatcher.ApiUrls;
import com.taxidispatcher.TestBaseClient;
import com.taxidispatcher.modules.account.adapter.web.dto.request.PasswordLoginRequest;
import com.taxidispatcher.modules.account.adapter.web.dto.request.RegisterBasicRequest;
import com.taxidispatcher.modules.account.adapter.web.dto.response.LoginResponse;
import com.taxidispatcher.modules.account.domain.model.IdentifierKind;
import com.taxidispatcher.modules.user.adapter.web.dto.request.RegisterUserRequest;
import com.taxidispatcher.modules.user.adapter.web.dto.request.UpdateUserAddressRequest;
import com.taxidispatcher.modules.user.adapter.web.dto.request.UpdateUserNameRequest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// 사용자 로그인시 외부 API 호출로 인한 웹 설정
public class UserApiTest extends TestBaseClient {
    private final String loginId = "tester_001";
    private final String password = "test012!";

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

    @Test
    void fullTest() throws Exception {
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
        loginToken = "Bearer " + accountApi_User();

        // 사용자 이름 변경
        UpdateUserNameRequest nameRequest = new UpdateUserNameRequest("테스터(이름수정)");
        var chgNameRes = patchJson(ApiUrls.User.UPDATE_NAME, convertString(nameRequest), loginToken)
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("사용자 이름 변경 Response\n"
                + chgNameRes.getResponse().getContentAsString());

        // 사용자 주소 변경
        UpdateUserAddressRequest addressRequest = new UpdateUserAddressRequest("서울 송파구 송파대로 558", "월드타워 57층");
        var chgAddressRes = patchJson(ApiUrls.User.UPDATE_ADDRESS, convertString(addressRequest), loginToken)
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("사용자 주소 변경 Response\n"
                + chgAddressRes.getResponse().getContentAsString());
        
        // 사용자 삭제
        var delRes = deleteJson(ApiUrls.User.DELETE, null, loginToken)
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("사용자 삭제 Response\n"
                + delRes.getResponse().getContentAsString());
    }
}
