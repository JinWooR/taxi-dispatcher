package com.taxidispatcher.modules.driver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxidispatcher.core.TestBase;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.InternalDriverNearbyGeoRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class InternalDriverNearbyTest extends TestBase {
    private final InternalDriverApiHelper driverApiHelper;

    public InternalDriverNearbyTest(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
        this.driverApiHelper = new InternalDriverApiHelper(mockMvc, objectMapper);
    }

    @Test
    void fullTest() throws Exception {
        // 주변 기사 조회 (빈 리스트)
        driverApiHelper.nearbyGeoDriver(new InternalDriverNearbyGeoRequest(Collections.emptyList(), 37.5490d, 127.0812d, 50));
        // 주변 기사 조회 (아무 기사 아이디 입력)
        driverApiHelper.nearbyGeoDriver(new InternalDriverNearbyGeoRequest(List.of(UUID.randomUUID()), 37.5490d, 127.0812d, 50));
    }
}
