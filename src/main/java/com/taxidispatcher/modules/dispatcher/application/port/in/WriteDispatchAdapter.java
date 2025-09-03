package com.taxidispatcher.modules.dispatcher.application.port.in;

import com.taxidispatcher.modules.dispatcher.domain.aggregate.Dispatch;

public interface WriteDispatchAdapter {
    Dispatch handle(WriteDispatchCommand command);
}
