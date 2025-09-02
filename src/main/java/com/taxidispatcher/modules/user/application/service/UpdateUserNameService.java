package com.taxidispatcher.modules.user.application.service;

import com.taxidispatcher.modules.user.adapter.persistence.jpa.repository.UserRepository;
import com.taxidispatcher.modules.user.adapter.web.dto.response.UserResponse;
import com.taxidispatcher.modules.user.application.port.in.UpdateUserNameCommand;
import com.taxidispatcher.modules.user.application.port.in.UpdateUserNameUseCase;
import com.taxidispatcher.modules.user.domain.aggregate.User;
import com.taxidispatcher.modules.user.domain.model.Address;
import com.taxidispatcher.modules.user.domain.model.City;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserNameService implements UpdateUserNameUseCase {
    private final UserRepository userRepository;

    @Override
    public UserResponse handle(UpdateUserNameCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "존재하지 않는 회원정보입니다."));

        user.updateName(command.name());

        user = userRepository.save(user);

        Address address = user.getAddress();
        City city = user.getCity();

        return new UserResponse(user.getUserId().value().toString(), user.getName(), address.getAddress(), address.getAddressDetail(), city.sd(), city.sgg(), city.emd());
    }
}
