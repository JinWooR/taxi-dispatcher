package com.taxidispatcher.modules.driver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxidispatcher.ApiUrls;
import com.taxidispatcher.core.TestHelper;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.RegisterDriverRequest;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.UpdateDriverActiveStatusRequest;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.UpdateDriverGeoRequest;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.UpdateDriverTaxiRequest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DriverApiHelper extends TestHelper {
    private String token = null;

    public DriverApiHelper(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
    }

    public void setToken(String token) {
        this.token = token;
    }

    /** 기사 등록 */
    public String register(RegisterDriverRequest req) throws Exception {
        var res = postJson(ApiUrls.Driver.REGISTER, convertString(req), token)
                .andExpect(status().isCreated())
                .andReturn();

        System.out.println("기사 등록 Response\n"
                + res.getResponse().getContentAsString());

        return res.getResponse().getContentAsString();
    }

    /** 택시 정보 변경 */
    public String updateTaxi(UpdateDriverTaxiRequest req) throws Exception {
        var res = patchJson(ApiUrls.Driver.UPDATE_TAXI, convertString(req), token)
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("택시 정보 변경 Response\n"
                + res.getResponse().getContentAsString());

        return res.getResponse().getContentAsString();
    }

    /** 기사 상태 변경 */
    public String updateActiveStatus(UpdateDriverActiveStatusRequest req) throws Exception {
        var res = postJson(ApiUrls.Driver.UPDATE_ACTIVE_STATUS, convertString(req), token)
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("기사 상태 변경 Response\n"
                + res.getResponse().getContentAsString());

        return res.getResponse().getContentAsString();
    }

    /** 좌표 최신화 */
    public String updateGeo(UpdateDriverGeoRequest req) throws Exception {
        var res = postJson(ApiUrls.Driver.UPDATE_GEO, convertString(req), token)
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("현재 좌표 최신화 Response\n"
                + res.getResponse().getContentAsString());

        return res.getResponse().getContentAsString();
    }

    /** 기사 삭제 */
    public void delete() throws Exception {
        // 기사 삭제
        var delRes = deleteJson(ApiUrls.Driver.DELETE, null, token)
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("기사 삭제 Response\n"
                + delRes.getResponse().getContentAsString());
    }
}
