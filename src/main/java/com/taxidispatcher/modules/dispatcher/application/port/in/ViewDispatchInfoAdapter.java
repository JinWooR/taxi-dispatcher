package com.taxidispatcher.modules.dispatcher.application.port.in;

import com.taxidispatcher.modules.dispatcher.adapter.web.dto.response.DispatchInfoResponse;

public interface ViewDispatchInfoAdapter {
    DispatchInfoResponse handle(ViewDispatchInfoCommand command);
}
