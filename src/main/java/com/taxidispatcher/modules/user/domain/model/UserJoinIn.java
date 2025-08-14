package com.taxidispatcher.modules.user.domain.model;

import com.taxidispatcher.modules.user.domain.model.value.*;
import lombok.Value;

@Value
public class UserJoinIn {
    private final UserId userId;

    private final String name;

    private final City city;
    private final Address address;
    private final Email email;
    private final Phone phone;
}
