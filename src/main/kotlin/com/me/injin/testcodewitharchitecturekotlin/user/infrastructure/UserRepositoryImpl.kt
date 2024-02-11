package com.me.injin.testcodewitharchitecturekotlin.user.infrastructure

import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus
import com.me.injin.testcodewitharchitecturekotlin.user.service.port.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository,
) : UserRepository {
    override fun findByIdAndStatus(id: Long, userStatus: UserStatus): UserEntity? {
        return userJpaRepository.findByIdAndStatus(id, userStatus)
    }

    override fun findByEmailAndStatus(email: String?, userStatus: UserStatus): UserEntity? {
        return userJpaRepository.findByEmailAndStatus(email, userStatus)
    }

    override fun save(userEntity: UserEntity): UserEntity {
        return userJpaRepository.save(userEntity)
    }

    override fun findById(id: Long): UserEntity? {
        return userJpaRepository.findById(id).orElse(null)
    }

}
