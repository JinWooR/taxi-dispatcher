package com.taxidispatcher.modules.user.application.service;

import com.taxidispatcher.modules.user.adapter.persistence.jpa.repository.UserRepository;
import com.taxidispatcher.modules.user.adapter.web.dto.response.InternalUserResponse;
import com.taxidispatcher.modules.user.application.port.in.InternalSearchUserIdUseCase;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InternalSearchUserIdService implements InternalSearchUserIdUseCase {
    private final UserRepository userRepository;

    @Override
    public InternalUserResponse handle(InternalSearchUserIdCommand command) {
        var user = userRepository.findById(command.userId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "사용자 정보를 확인할 수 없습니다."));

        return new InternalUserResponse(
                user.getUserId().value(),
                user.getName()
        );
    }
}
