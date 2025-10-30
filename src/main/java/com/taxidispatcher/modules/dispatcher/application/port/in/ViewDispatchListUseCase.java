package com.taxidispatcher.modules.dispatcher.application.port.in;

import com.taxidispatcher.modules.dispatcher.adapter.web.dto.response.DispatchListResponse;

import java.util.List;
import java.util.UUID;

public interface ViewDispatchListUseCase {
    List<DispatchListResponse> handle(ViewDispatchListCommand command);

    record ViewDispatchListCommand(
            UUID userId,
            UUID driverId
    ) {}
}
