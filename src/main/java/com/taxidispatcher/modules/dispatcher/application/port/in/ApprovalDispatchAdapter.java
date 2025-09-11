package com.taxidispatcher.modules.dispatcher.application.port.in;

public interface ApprovalDispatchAdapter {
    void handle(ApprovalDispatchCommand command);
}
