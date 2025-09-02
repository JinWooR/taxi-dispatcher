package com.taxidispatcher.modules.user.application.service;

import com.taxidispatcher.modules.user.adapter.persistence.jpa.repository.UserRepository;
import com.taxidispatcher.modules.user.adapter.web.dto.response.InternalUserAccountResponse;
import com.taxidispatcher.modules.user.application.port.in.InternalSearchUserAccountCommand;
import com.taxidispatcher.modules.user.application.port.in.InternalSearchUserAccountUseCase;
import com.taxidispatcher.modules.user.domain.aggregate.User;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InternalSearchUserAccountUseCaseService implements InternalSearchUserAccountUseCase {
    private final UserRepository userRepository;

    @Override
    public InternalUserAccountResponse handle(InternalSearchUserAccountCommand command) {
        User user = userRepository.findByAccountId(command.accountId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "회원 정보를 조회할 수 없습니다."));
        return new InternalUserAccountResponse(user.getUserId().value());
    }
}
