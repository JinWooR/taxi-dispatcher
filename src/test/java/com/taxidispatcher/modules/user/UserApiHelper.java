package com.taxidispatcher.modules.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxidispatcher.ApiUrls;
import com.taxidispatcher.core.TestHelper;
import com.taxidispatcher.modules.user.adapter.web.dto.request.RegisterUserRequest;
import com.taxidispatcher.modules.user.adapter.web.dto.request.UpdateUserAddressRequest;
import com.taxidispatcher.modules.user.adapter.web.dto.request.UpdateUserNameRequest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserApiHelper extends TestHelper {
    private String token = null;

    public UserApiHelper(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
    }

    public void setToken(String token) {
        this.token = token;
    }

    /** 사용자 등록 */
    public String register(RegisterUserRequest req) throws Exception {
        // 사용자 등록
        var res = postJson(ApiUrls.User.REGISTER, convertString(req), token)
                .andExpect(status().isCreated())
                .andReturn();

        System.out.println("사용자 등록 Response\n"
                + res.getResponse().getContentAsString());

        return res.getResponse().getContentAsString();
    }

    /** 이름 변경 */
    public String updateName(UpdateUserNameRequest req) throws Exception {
        // 사용자 이름 변경
        var res = patchJson(ApiUrls.User.UPDATE_NAME, convertString(req), token)
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("사용자 이름 변경 Response\n"
                + res.getResponse().getContentAsString());

        return res.getResponse().getContentAsString();
    }

    /** 주소 변경 */
    public String updateAddress(UpdateUserAddressRequest req) throws Exception {
        // 사용자 주소 변경
        var res = patchJson(ApiUrls.User.UPDATE_ADDRESS, convertString(req), token)
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("사용자 주소 변경 Response\n"
                + res.getResponse().getContentAsString());

        return res.getResponse().getContentAsString();
    }

    /** 삭제 */
    public void delete() throws Exception {
        // 사용자 삭제
        var delRes = deleteJson(ApiUrls.User.DELETE, null, token)
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("사용자 삭제 Response\n"
                + delRes.getResponse().getContentAsString());
    }
}