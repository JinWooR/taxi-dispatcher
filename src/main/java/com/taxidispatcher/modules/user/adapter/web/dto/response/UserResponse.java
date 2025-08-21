package com.taxidispatcher.modules.user.adapter.web.dto.response;

public record UserResponse(
        String userId,
        String name,
        String address,
        String addressDetail,
        String sd,
        String sgg,
        String emd
) {
}
