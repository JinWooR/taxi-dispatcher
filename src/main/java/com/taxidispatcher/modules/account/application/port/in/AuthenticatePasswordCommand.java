package com.taxidispatcher.modules.account.application.port.in;

/**
 * 
 * @param loginId 로그인 아이디
 * @param rawPassword 비밀번호 (Plain Text)
 */
public record AuthenticatePasswordCommand(
        String loginId,
        String rawPassword
) {
}
