package com.taxidispatcher.modules.driver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxidispatcher.ApiUrls;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 기사 로그인시 외부 API 호출로 인한 웹 설정
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class DriverApiTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final String loginId = "tester_001";
    private final String password = "test012!";

    private String accountApi() throws Exception {
        // 어카운트 등록
        RegisterBasicRequest registerBasicRequest = new RegisterBasicRequest(
                IdentifierKind.ID,
                loginId,
                password
        );

        mockMvc
                .perform(
                        post(ApiUrls.Account.REGISTER)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(registerBasicRequest))
                )
                .andExpect(status().isCreated());

        // 로그인
        PasswordLoginRequest loginRequest = new PasswordLoginRequest(
                loginId, password
        );
        var loginRes = mockMvc
                .perform(
                        post(ApiUrls.Account.LOGIN_BASIC)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginRequest))
                )
                .andExpect(status().isOk())
                .andReturn();

        String loginToken = null;
        if (loginRes.getResponse().getStatus() >= 200 && loginRes.getResponse().getStatus() < 300) {
            loginToken = objectMapper.readValue(loginRes.getResponse().getContentAsString(), LoginResponse.class)
                    .token();
        }

        return loginToken;
    }

    private String accountApi_Driver() throws Exception {

        // 로그인
        PasswordLoginRequest loginRequest = new PasswordLoginRequest(
                loginId, password
        );
        var loginRes = mockMvc
                .perform(
                        post(ApiUrls.Account.LOGIN_BASIC_DRIVER)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginRequest))
                )
                .andExpect(status().isOk())
                .andReturn();

        String loginToken = null;
        if (loginRes.getResponse().getStatus() >= 200 && loginRes.getResponse().getStatus() < 300) {
            loginToken = objectMapper.readValue(loginRes.getResponse().getContentAsString(), LoginResponse.class)
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
        var registerRes = mockMvc
                .perform(
                        post(ApiUrls.Driver.REGISTER)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", loginToken)
                                .content(objectMapper.writeValueAsString(driverRequest))
                )
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
        var chgNameRes = mockMvc
                .perform(
                        patch(ApiUrls.Driver.UPDATE_TAXI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", loginToken)
                                .content(objectMapper.writeValueAsString(taxiRequest))
                )
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("택시 정보 변경 Response\n"
                + chgNameRes.getResponse().getContentAsString());

        // 택시 출근
        UpdateDriverActiveStatusRequest activeStatusWaitingReq = new UpdateDriverActiveStatusRequest(DriverActiveStatus.WAITING);
        var waitingRes = mockMvc
                .perform(
                        post(ApiUrls.Driver.UPDATE_ACTIVE_STATUS)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", loginToken)
                                .content(objectMapper.writeValueAsString(activeStatusWaitingReq))
                )
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("택시 출근 Response\n"
                + waitingRes.getResponse().getContentAsString());
        
        // 현재 좌표 최신화
        UpdateDriverGeoRequest geoRequest = new UpdateDriverGeoRequest(
                "37.504467", "127.098760",
                Instant.now(Clock.systemUTC()), 1L
        );
        var geoRes = mockMvc
                .perform(
                        post(ApiUrls.Driver.UPDATE_GEO)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", loginToken)
                                .content(objectMapper.writeValueAsString(geoRequest))
                )
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("현재 좌표 최신화 Response\n"
                + geoRes.getResponse().getContentAsString());

        // 택시 퇴근
        UpdateDriverActiveStatusRequest activeStatusLeaveWorkReq = new UpdateDriverActiveStatusRequest(DriverActiveStatus.LEAVE_WORK);
        var leaveWorkRes = mockMvc
                .perform(
                        post(ApiUrls.Driver.UPDATE_ACTIVE_STATUS)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", loginToken)
                                .content(objectMapper.writeValueAsString(activeStatusLeaveWorkReq))
                )
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("택시 퇴근 Response\n"
                + leaveWorkRes.getResponse().getContentAsString());

        // 기사 삭제
        var delRes = mockMvc
                .perform(
                        delete(ApiUrls.Driver.DELETE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", loginToken)
                )
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("기사 삭제 Response\n"
                + delRes.getResponse().getContentAsString());
    }


}
