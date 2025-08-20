package com.taxidispatcher.modules.user.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.user.adapter.persistence.jpa.entity.UserJpaEntity;
import com.taxidispatcher.modules.user.adapter.persistence.mapper.UserMapper;
import com.taxidispatcher.modules.user.domain.aggregate.User;
import com.taxidispatcher.modules.user.domain.model.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findById(UserId userId) {
        var userJpaEntity = userJpaRepository.findById(userId.value())
                .orElse(null);

        return Optional.ofNullable(userMapper.toDomain(userJpaEntity));
    }

    @Override
    public Optional<User> findByAccountId(UUID accountId) {
        var userJpaEntity = userJpaRepository.findByAccountId(accountId)
                .orElse(null);

        return Optional.ofNullable(userMapper.toDomain(userJpaEntity));
    }

    @Override
    public boolean existsById(UserId userId) {
        return findById(userId).isPresent();
    }

    @Override
    public User save(User user) {
        var userJpaEntity = userJpaRepository.findById(user.getUserId().value())
                .orElse(null);

        if (userJpaEntity == null) {
            userJpaEntity = userMapper.toJpa(user);
            return userMapper.toDomain(userJpaRepository.save(userJpaEntity));
        }

        mergeUser(userJpaEntity, user);

        return userMapper.toDomain(userJpaRepository.save(userJpaEntity));
    }

    private void mergeUser(UserJpaEntity userJpaEntity, User user) {
        userJpaEntity.setName(user.getName());
        userJpaEntity.setUserStatus(user.getStatus());
        userJpaEntity.setSd(user.getCity().getSd());
        userJpaEntity.setSgg(user.getCity().getSgg());
        userJpaEntity.setEmd(user.getCity().getEmd());
        userJpaEntity.setAddress(user.getAddress().getAddress());
        userJpaEntity.setAddressDetail(user.getAddress().addressDetail());
    }

    @Override
    public void delete(UserId userId) {
        var userJpaEntity = userJpaRepository.findById(userId.value())
                .orElse(null);

        if (userJpaEntity == null) return;

        userJpaRepository.delete(userJpaEntity);
    }
}
