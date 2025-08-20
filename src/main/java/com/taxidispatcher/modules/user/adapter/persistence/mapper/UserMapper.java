package com.taxidispatcher.modules.user.adapter.persistence.mapper;

import com.taxidispatcher.modules.user.adapter.persistence.jpa.entity.UserJpaEntity;
import com.taxidispatcher.modules.user.domain.aggregate.User;
import com.taxidispatcher.modules.user.domain.model.Address;
import com.taxidispatcher.modules.user.domain.model.City;
import com.taxidispatcher.modules.user.domain.model.UserId;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(UserJpaEntity userJpaEntity) {
        if (userJpaEntity == null) return null;

        UserId userId = UserId.of(userJpaEntity.getId());
        City city = City.of(userJpaEntity.getSd(), userJpaEntity.getSgg(), userJpaEntity.getEmd());
        Address address = Address.of(userJpaEntity.getAddress(), userJpaEntity.getAddressDetail());

        return User.of(userId, userJpaEntity.getAccountId(), userJpaEntity.getUserStatus(), userJpaEntity.getName(), city, address);
    }

    public UserJpaEntity toJpa(User user) {
        return new UserJpaEntity(user.getUserId().value(), user.getStatus(), user.getAccountId(), user.getName(), user.getCity().getSd(), user.getCity().getSgg(), user.getCity().getEmd(), user.getAddress().address(), user.getAddress().addressDetail());
    }
}
