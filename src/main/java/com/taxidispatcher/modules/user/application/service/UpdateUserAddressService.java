package com.taxidispatcher.modules.user.application.service;

import com.taxidispatcher.modules.user.adapter.persistence.jpa.repository.UserRepository;
import com.taxidispatcher.modules.user.adapter.web.dto.response.UserResponse;
import com.taxidispatcher.modules.user.application.port.in.UpdateUserAddressCommand;
import com.taxidispatcher.modules.user.application.port.in.UpdateUserAddressUseCase;
import com.taxidispatcher.modules.user.application.port.out.AddressInfoSearcher;
import com.taxidispatcher.modules.user.domain.aggregate.User;
import com.taxidispatcher.modules.user.domain.model.Address;
import com.taxidispatcher.modules.user.domain.model.City;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserAddressService implements UpdateUserAddressUseCase {
    private final UserRepository userRepository;
    private final @Qualifier("kakao") AddressInfoSearcher addressInfoSearcher;

    @Override
    public UserResponse handle(UpdateUserAddressCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원정보입니다."));

        AddressInfoSearcher.AddressInfo addressInfo = addressInfoSearcher.search(command.address());

        user.updateAddress(Address.of(command.address(), command.addressDetail()));
        user.updateCity(City.of(addressInfo.sd(), addressInfo.sgg(), addressInfo.emd()));
        user = userRepository.save(user);

        Address address = user.getAddress();
        City city = user.getCity();

        return new UserResponse(user.getUserId().value().toString(), user.getName(), address.getAddress(), address.getAddressDetail(), city.sd(), city.sgg(), city.emd());
    }
}
