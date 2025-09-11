package com.taxidispatcher.modules.dispatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxidispatcher.ApiUrls;
import com.taxidispatcher.core.TestHelper;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DriverDispatchApiHelper extends TestHelper {
    private String token;

    public DriverDispatchApiHelper(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String approval(UUID dispatchId) throws Exception {
        var res = postJson(ApiUrls.Dispatch.Driver.APPROVAL.replace("{dispatchId}", dispatchId.toString()), null, token)
                .andExpect(status().isOk())
                .andReturn();

        return res.getResponse().getContentAsString();
    }

    public String refusal(UUID dispatchId) throws Exception {
        var res = postJson(ApiUrls.Dispatch.Driver.REFUSAL.replace("{dispatchId}", dispatchId.toString()), null, token)
                .andExpect(status().isOk())
                .andReturn();

        return res.getResponse().getContentAsString();
    }
}
