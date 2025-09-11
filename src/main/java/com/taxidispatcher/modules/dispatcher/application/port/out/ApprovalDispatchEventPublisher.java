package com.taxidispatcher.modules.dispatcher.application.port.out;

import com.taxidispatcher.modules.dispatcher.domain.event.ApprovalDispatchEvent;

public interface ApprovalDispatchEventPublisher {
    void handle(ApprovalDispatchEvent event);
}
