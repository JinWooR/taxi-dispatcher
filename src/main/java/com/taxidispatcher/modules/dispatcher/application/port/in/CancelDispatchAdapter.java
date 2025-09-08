package com.taxidispatcher.modules.dispatcher.application.port.in;

public interface CancelDispatchAdapter {
    void handle(CancelDispatchCommand command);
}
