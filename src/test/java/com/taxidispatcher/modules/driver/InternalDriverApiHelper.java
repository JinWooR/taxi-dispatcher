package com.taxidispatcher.modules.driver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxidispatcher.ApiUrls;
import com.taxidispatcher.core.TestHelper;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.InternalDriverNearbyGeoRequest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InternalDriverApiHelper extends TestHelper {
    public InternalDriverApiHelper(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
    }

    public String account(String accountId) throws Exception {
        var res = getJson(ApiUrls.Driver.Internal.ACCOUNT.replace("{id}", accountId), null)
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        System.out.println("기사 어카운트 조회\n"
                + res.getRequest().getContentAsString());

        return res.getResponse().getContentAsString();
    }

    /** 주변 기사 조회 */
    public String nearbyGeoDriver(InternalDriverNearbyGeoRequest req) throws Exception {
        var res = postJson(ApiUrls.Driver.Internal.NEARBY_GEO, convertString(req))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        System.out.println("주변 기사 검색\n"
                + res.getRequest().getContentAsString());

        return res.getResponse().getContentAsString();
    }
}
