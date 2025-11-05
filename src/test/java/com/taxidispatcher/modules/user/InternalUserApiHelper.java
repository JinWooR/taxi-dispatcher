package com.taxidispatcher.modules.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxidispatcher.ApiUrls;
import com.taxidispatcher.core.TestHelper;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InternalUserApiHelper extends TestHelper {
    public InternalUserApiHelper(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
    }

    public String account(String accountId) throws Exception {
        var res = getJson(ApiUrls.User.Internal.accountId(accountId))
                .andExpect(status().isOk())
                .andReturn();

        log("내부 API) 사용자 어카운트 조회 Response\n"
                + res.getResponse().getContentAsString());

        return res.getResponse().getContentAsString();
    }

    public String userInfo(String userId) throws Exception {
        var res = getJson(ApiUrls.User.Internal.info(userId))
                .andExpect(status().isOk())
                .andReturn();

        log("내부 API) 사용자 정보 조회 Response\n"
                + res.getResponse().getContentAsString());

        return res.getResponse().getContentAsString();
    }
}
