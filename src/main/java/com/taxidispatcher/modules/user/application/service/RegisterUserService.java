package com.taxidispatcher.modules.user.application.service;

import com.taxidispatcher.modules.user.adapter.persistence.jpa.repository.UserRepository;
import com.taxidispatcher.modules.user.application.port.in.RegisterUserCommand;
import com.taxidispatcher.modules.user.application.port.in.RegisterUserUseCase;
import com.taxidispatcher.modules.user.application.port.out.AddressInfoSearcher;
import com.taxidispatcher.modules.user.domain.aggregate.User;
import com.taxidispatcher.modules.user.domain.model.Address;
import com.taxidispatcher.modules.user.domain.model.City;
import com.taxidispatcher.modules.user.domain.model.UserId;
import com.taxidispatcher.modules.user.domain.model.UserStatus;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {
    private final UserRepository userRepository;
    private final @Qualifier("kakao") AddressInfoSearcher addressInfoSearcher;

    @Override
    public UserId handle(RegisterUserCommand command) {
        if (userRepository.findByAccountId(command.accountId()).isPresent()) {
            throw new AppException(ErrorCode.CONFLICT, "해당 어카운트는 이미 등록된 사용자가 존재합니다.");
        }

        AddressInfoSearcher.AddressInfo addressInfo = addressInfoSearcher.search(command.address());

        UserId newId = UserId.newId();
        City city = City.of(addressInfo.sd(), addressInfo.sgg(), addressInfo.emd());
        Address address = Address.of(command.address(), command.addressDetail());

        User newUser = User.of(newId, command.accountId(), UserStatus.ACTIVE, command.name(), city, address);

        newUser = userRepository.save(newUser);

        return newUser.getUserId();
    }
}
