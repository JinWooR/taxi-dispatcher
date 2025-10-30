package com.taxidispatcher.modules.dispatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxidispatcher.ApiUrls;
import com.taxidispatcher.core.TestHelper;
import com.taxidispatcher.modules.dispatcher.adapter.web.dto.request.WriteDispatchRequest;
import com.taxidispatcher.modules.dispatcher.adapter.web.dto.response.DispatchInfoResponse;
import com.taxidispatcher.modules.dispatcher.adapter.web.dto.response.WriteDispatchResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// TODO. DispatchApiHelper -> UserDispatchApiHelper 변경 필요
public class DispatchApiHelper extends TestHelper {
    private String token;

    public DispatchApiHelper(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String list() throws Exception {
        var res = getJson(ApiUrls.Dispatch.User.LIST, token)
                .andExpect(status().isOk())
                .andReturn();
        
        System.out.println("사용자) 배차 목록 조회\n"
                + res.getResponse().getContentAsString());
        
        return res.getResponse().getContentAsString();
    }

    public DispatchInfoResponse info(UUID dispatchId) throws Exception {
        var res = getJson(ApiUrls.Dispatch.User.INFO.replace("{dispatchId}", dispatchId.toString()), token)
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("사용자) 배차 정보 확인\n"
                + res.getResponse().getContentAsString());

        return read(res.getResponse().getContentAsString(), DispatchInfoResponse.class);
    }

    public Optional<WriteDispatchResponse> write(WriteDispatchRequest req) throws Exception {
        var res = postJson(ApiUrls.Dispatch.User.WRITE, convertString(req), token)
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        System.out.println("사용자) 배차 요청\n"
                + res.getResponse().getContentAsString());

        var dispatch = read(res.getResponse().getContentAsString(), WriteDispatchResponse.class);
        return Optional.ofNullable(dispatch);
    }

    public String cancel(String dispatchId) throws Exception {
        var res = patchJson(ApiUrls.Dispatch.User.CANCEL.replace("{dispatchId}", dispatchId), null, token)
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return res.getResponse().getContentAsString();
    }
}
