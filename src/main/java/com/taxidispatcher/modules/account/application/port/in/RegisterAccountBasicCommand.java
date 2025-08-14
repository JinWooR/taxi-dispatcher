package com.taxidispatcher.modules.account.application.port.in;

import com.taxidispatcher.modules.account.domain.model.IdentifierKind;


/**
 * @see com.taxidispatcher.modules.account.domain.model.LoginIdentifier
 * 암호화된 비밀번호가 필요하여 VO를 인자로 요구 X
 *
 * @param identifierKind 로그인 아이디 식별 종류
 * @param loginId 로그인 아이디
 * @param rawPassword 비밀번호 (Plain Text)
 */
public record RegisterAccountBasicCommand(
        IdentifierKind identifierKind,
        String loginId,
        String rawPassword
) {}
