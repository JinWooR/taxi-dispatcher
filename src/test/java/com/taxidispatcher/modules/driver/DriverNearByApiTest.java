package com.taxidispatcher.modules.driver;

import com.taxidispatcher.ApiUrls;
import com.taxidispatcher.TestBaseClient;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.InternalDriverNearbyGeoRequest;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DriverNearByApiTest extends TestBaseClient {

    @Test
    void fullTest() throws Exception {
        var req = new InternalDriverNearbyGeoRequest(Collections.emptyList(), 37.5490, 127.0812, 50);

        var res = postJson(ApiUrls.Driver.Internal.NEARBY_GEO, convertString(req))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        System.out.println("주변 기사 검색\n"
                + res.getRequest().getContentAsString());
    }
}
