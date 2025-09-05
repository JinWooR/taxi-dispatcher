package com.taxidispatcher.modules.driver;

import com.taxidispatcher.ApiUrls;
import com.taxidispatcher.TestBaseClient;
import com.taxidispatcher.modules.account.adapter.web.dto.request.PasswordLoginRequest;
import com.taxidispatcher.modules.account.adapter.web.dto.request.RegisterBasicRequest;
import com.taxidispatcher.modules.account.adapter.web.dto.response.LoginResponse;
import com.taxidispatcher.modules.account.domain.model.IdentifierKind;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.RegisterDriverRequest;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.UpdateDriverActiveStatusRequest;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.UpdateDriverGeoRequest;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.UpdateDriverTaxiRequest;
import com.taxidispatcher.modules.driver.domain.model.DriverActiveStatus;
import com.taxidispatcher.modules.driver.domain.model.TaxiColor;
import com.taxidispatcher.modules.driver.domain.model.TaxiSize;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 기사 로그인시 외부 API 호출로 인한 웹 설정
public class DriverApiTest extends TestBaseClient {
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

    private String accountApi_Driver() throws Exception {

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

    @Test
    void fullTest() throws Exception {
        // 어카운트 등록 및 로그인(+토큰 발급)
        String loginToken = "Bearer " + accountApi();

        // 기사 등록
        RegisterDriverRequest driverRequest = new RegisterDriverRequest(
                "테스터",
                "서울30나2397",
                TaxiSize.MEDIUM,
                TaxiColor.BLACK,
                null
        );
        var registerRes = postJson(ApiUrls.Driver.REGISTER, convertString(driverRequest), loginToken)
                .andExpect(status().isCreated())
                .andReturn();

        System.out.println("기사 등록 Response\n"
                + registerRes.getResponse().getContentAsString());

        // 기사 권한 로그인(+토큰 발급)
        loginToken = "Bearer " + accountApi_Driver();

        // 택시 정보 변경
        UpdateDriverTaxiRequest taxiRequest = new UpdateDriverTaxiRequest(
                "서울03바1273",
                TaxiSize.SMALL,
                TaxiColor.OTHER,
                "노란색"
        );
        var chgNameRes = patchJson(ApiUrls.Driver.UPDATE_TAXI, convertString(taxiRequest), loginToken)
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("택시 정보 변경 Response\n"
                + chgNameRes.getResponse().getContentAsString());

        // 택시 출근
        UpdateDriverActiveStatusRequest activeStatusWaitingReq = new UpdateDriverActiveStatusRequest(DriverActiveStatus.WAITING);
        var waitingRes = postJson(ApiUrls.Driver.UPDATE_ACTIVE_STATUS, convertString(activeStatusWaitingReq), loginToken)
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("택시 출근 Response\n"
                + waitingRes.getResponse().getContentAsString());
        
        // 현재 좌표 최신화
        UpdateDriverGeoRequest geoRequest = new UpdateDriverGeoRequest(
                37.504467, 127.098760,
                Instant.now(Clock.systemUTC()), 1L
        );
        var geoRes = postJson(ApiUrls.Driver.UPDATE_GEO, convertString(geoRequest), loginToken)
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("현재 좌표 최신화 Response\n"
                + geoRes.getResponse().getContentAsString());

        // 택시 퇴근
        UpdateDriverActiveStatusRequest activeStatusLeaveWorkReq = new UpdateDriverActiveStatusRequest(DriverActiveStatus.LEAVE_WORK);
        var leaveWorkRes = postJson(ApiUrls.Driver.UPDATE_ACTIVE_STATUS, convertString(activeStatusLeaveWorkReq), loginToken)
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("택시 퇴근 Response\n"
                + leaveWorkRes.getResponse().getContentAsString());

        // 기사 삭제
        var delRes = deleteJson(ApiUrls.Driver.DELETE, null, loginToken)
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("기사 삭제 Response\n"
                + delRes.getResponse().getContentAsString());
    }


}
