package com.taxidispatcher.modules.dispatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxidispatcher.core.TestBase;
import com.taxidispatcher.modules.account.AccountApiHelper;
import com.taxidispatcher.modules.dispatcher.adapter.web.dto.request.WriteDispatchRequest;
import com.taxidispatcher.modules.driver.DriverApiHelper;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.RegisterDriverRequest;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.UpdateDriverActiveStatusRequest;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.UpdateDriverGeoRequest;
import com.taxidispatcher.modules.driver.domain.model.DriverActiveStatus;
import com.taxidispatcher.modules.driver.domain.model.TaxiColor;
import com.taxidispatcher.modules.driver.domain.model.TaxiSize;
import com.taxidispatcher.modules.user.UserApiHelper;
import com.taxidispatcher.modules.user.adapter.web.dto.request.RegisterUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

public class DispatchTest extends TestBase {
    private final AccountApiHelper accountApiHelper;
    private final AccountApiHelper accountApiHelper_driver;
    private final AccountApiHelper accountApiHelper_refusalDriver;
    private final UserApiHelper userApiHelper;
    private final DriverApiHelper driverApiHelper;
    private final DispatchApiHelper dispatchApiHelper;
    private final DriverDispatchApiHelper driverDispatchApiHelper;

    public DispatchTest(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
        this.accountApiHelper = new AccountApiHelper(mockMvc, objectMapper, "user001", "user012!");
        this.accountApiHelper_driver = new AccountApiHelper(mockMvc, objectMapper, "driver001", "driver001!@");
        this.accountApiHelper_refusalDriver = new AccountApiHelper(mockMvc, objectMapper, "refusal_drvier001", "refusal_drvier001!@");
        this.userApiHelper = new UserApiHelper(mockMvc, objectMapper);
        this.driverApiHelper = new DriverApiHelper(mockMvc, objectMapper);
        this.dispatchApiHelper = new DispatchApiHelper(mockMvc, objectMapper);
        this.driverDispatchApiHelper = new DriverDispatchApiHelper(mockMvc, objectMapper);
    }

    @Test
    void fullTest() throws Exception {
        String userToken = register_user(); // 사용자 토큰
        String driverToken = register_driver(); // 기사 토큰
        String refusalDriverToken = register_refusalDriver(); // 배차 거부 기사 토큰

        // 거절 기사 토큰
        driverApiHelper.setToken(refusalDriverToken);
        // 거절 기사 출근
        driverApiHelper.updateActiveStatus(new UpdateDriverActiveStatusRequest(DriverActiveStatus.WAITING));
        // 거절 기사 좌표 정보 최신화
        driverApiHelper.updateGeo(new UpdateDriverGeoRequest(37.5712d, 126.9784d, Instant.now(), 1L));
        
        // 기사 토큰
        driverApiHelper.setToken(driverToken);
        // 기사 출근
        driverApiHelper.updateActiveStatus(new UpdateDriverActiveStatusRequest(DriverActiveStatus.WAITING));
        // 기사 좌표 정보 최신화
        driverApiHelper.updateGeo(new UpdateDriverGeoRequest(37.5712d, 126.9784d, Instant.now(), 1L));

        dispatchApiHelper.setToken(userToken);
        // 배차 요청
        var dispatch = dispatchApiHelper.write(
                new WriteDispatchRequest(
                        new WriteDispatchRequest.AddressGeo("ex)출발지", 37.5705d, 126.9774d),
                        new WriteDispatchRequest.AddressGeo("ex)목적지", 37.5490d, 127.0812d)
                )
        );

        // 배차 거절 기사 토큰 세팅 (배차 API)
        driverDispatchApiHelper.setToken(refusalDriverToken);
        // 배차 거절
        driverDispatchApiHelper.refusal(dispatch.get().dispatchId());

        // 배차 승인 기사 토큰 세팅
        driverDispatchApiHelper.setToken(driverToken);
        // 기사 해당 배차 요청 승인
        driverDispatchApiHelper.approval(dispatch.get().dispatchId());
        // 기사 상태 조회
        driverApiHelper.me();
        // 운행 시작
        driverDispatchApiHelper.start(dispatch.get().dispatchId());
        // 기사 상태 조회
        driverApiHelper.me();
        // 기사 좌표 최신화
        driverApiHelper.updateGeo(new UpdateDriverGeoRequest(37.5837d, 126.9809d, Instant.now(), 2L));
        // 기사 좌표 최신화
        driverApiHelper.updateGeo(new UpdateDriverGeoRequest(37.59d, 126.9899d, Instant.now(), 3L));
        // 운행 종료
        driverDispatchApiHelper.arrival(dispatch.get().dispatchId());
        // 기사 상태 조회
        driverApiHelper.me();

        // 배차 정보 조회
        dispatchApiHelper.info(dispatch.get().dispatchId());
    }

    // 어카운트 + 사용자 등록
    private String register_user() throws Exception {
        accountApiHelper.register();
        String token = "Bearer " + accountApiHelper.login();

        userApiHelper.setToken(token);
        userApiHelper.register(new RegisterUserRequest("테스터", "서울 강남구 가로수길 9", "13층"));

        return  "Bearer " + accountApiHelper.loginUser();
    }

    // 어카운트 + 기사 등록
    private String register_driver() throws Exception {
        accountApiHelper_driver.register();
        String token = "Bearer " + accountApiHelper_driver.login();

        driverApiHelper.setToken(token);
        driverApiHelper.register(new RegisterDriverRequest("테스터 기사", "서울 03사1234", TaxiSize.MEDIUM, TaxiColor.BLACK, null));

        return  "Bearer " + accountApiHelper_driver.loginDriver();
    }

    // 어카운트 + 기사 등록
    private String register_refusalDriver() throws Exception {
        accountApiHelper_refusalDriver.register();
        String token = "Bearer " + accountApiHelper_refusalDriver.login();

        driverApiHelper.setToken(token);
        driverApiHelper.register(new RegisterDriverRequest("테스터 거절 기사", "서울 13사1234", TaxiSize.LARGE, TaxiColor.SILVER, null));

        return  "Bearer " + accountApiHelper_refusalDriver.loginDriver();
    }
}
