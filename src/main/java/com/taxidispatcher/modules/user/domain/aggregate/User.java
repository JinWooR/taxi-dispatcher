package com.taxidispatcher.modules.user.domain.aggregate;

import com.taxidispatcher.modules.user.domain.model.Address;
import com.taxidispatcher.modules.user.domain.model.City;
import com.taxidispatcher.modules.user.domain.model.UserId;
import com.taxidispatcher.modules.user.domain.model.UserStatus;
import lombok.Getter;

import java.util.UUID;

public class User {
    @Getter
    private final UserId userId;
    @Getter
    private final UUID accountId;
    @Getter
    private UserStatus status;
    @Getter
    private String name;
    @Getter
    private City city;
    @Getter
    private Address address;

    private User(UserId userId, UUID accountId, UserStatus status, String name, City city, Address address) {
        this.userId = userId;
        this.accountId = accountId;
        this.status = status;
        this.name = name;
        this.city = city;
        this.address = address;
    }

    public static User of(UserId userId, UUID accountId, UserStatus status, String name, City city, Address address) {
        return new User(userId, accountId, status, name, city, address);
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateAddress(Address address) {
        this.address = address;
    }

    public void updateCity(City city) {
        this.city = city;
    }
}
