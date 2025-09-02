package com.taxidispatcher.modules.user.application.service;

import com.taxidispatcher.modules.user.adapter.persistence.jpa.repository.UserRepository;
import com.taxidispatcher.modules.user.application.port.in.DeleteUserCommand;
import com.taxidispatcher.modules.user.application.port.in.DeleteUserUseCase;
import com.taxidispatcher.modules.user.domain.aggregate.User;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserService implements DeleteUserUseCase {
    private final UserRepository userRepository;

    @Override
    public void handle(DeleteUserCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "존재하지 않는 회원 또는 이미 탈퇴한 회원 정보입니다."));

        userRepository.delete(user.getUserId());
    }
}
