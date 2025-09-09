package com.taxidispatcher.modules.driver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxidispatcher.core.TestBase;
import com.taxidispatcher.modules.account.AccountApiHelper;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.RegisterDriverRequest;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.UpdateDriverActiveStatusRequest;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.UpdateDriverGeoRequest;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.UpdateDriverTaxiRequest;
import com.taxidispatcher.modules.driver.domain.model.DriverActiveStatus;
import com.taxidispatcher.modules.driver.domain.model.TaxiColor;
import com.taxidispatcher.modules.driver.domain.model.TaxiSize;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.Instant;

public class DriverTest extends TestBase {
    private final AccountApiHelper accountApiHelper;
    private final DriverApiHelper driverApiHelper;

    public DriverTest(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
        this.accountApiHelper = new AccountApiHelper(mockMvc, objectMapper, "driver001", "driver001!");
        this.driverApiHelper = new DriverApiHelper(mockMvc,objectMapper);
    }

    @Test
    void fullTest() throws Exception {
        accountApiHelper.register();
        String token = "Bearer " + accountApiHelper.login();

        driverApiHelper.setToken(token);
        driverApiHelper.register(
                new RegisterDriverRequest(
                        "테스터",
                        "서울30나2397",
                        TaxiSize.MEDIUM,
                        TaxiColor.BLACK,
                        null
                )
        );

        token = "Bearer " + accountApiHelper.loginDriver();
        driverApiHelper.setToken(token);

        driverApiHelper.updateTaxi(
                new UpdateDriverTaxiRequest(
                        "서울03바1273",
                        TaxiSize.SMALL,
                        TaxiColor.OTHER,
                        "노란색"
                )
        );
        // 출근
        driverApiHelper.updateActiveStatus(new UpdateDriverActiveStatusRequest(DriverActiveStatus.WAITING));

        // 좌표 최신화
        driverApiHelper.updateGeo(
                new UpdateDriverGeoRequest(
                        37.504467d, 127.098760d,
                        Instant.now(Clock.systemUTC()), 1L
                )
        );

        // 퇴근
        driverApiHelper.updateActiveStatus(new UpdateDriverActiveStatusRequest(DriverActiveStatus.LEAVE_WORK));

        // 기사 삭제
        driverApiHelper.delete();


    }
}
