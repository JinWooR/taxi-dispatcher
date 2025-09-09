package com.taxidispatcher.modules.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxidispatcher.core.TestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class AccountTest extends TestBase {
    private final AccountApiHelper accountApiHelper;

    public AccountTest(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
        this.accountApiHelper = new AccountApiHelper(mockMvc, objectMapper, "tester_001", "test012!");
    }

    @Test
    void fullTest() throws Exception {
        accountApiHelper.register();

        accountApiHelper.login();
    }
}
