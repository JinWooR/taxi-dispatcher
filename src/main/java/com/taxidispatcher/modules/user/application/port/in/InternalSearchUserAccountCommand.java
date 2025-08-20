package com.taxidispatcher.modules.user.application.port.in;


import java.util.UUID;

public record InternalSearchUserAccountCommand(UUID accountId) {
}
