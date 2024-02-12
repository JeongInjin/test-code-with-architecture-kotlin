package com.me.injin.testcodewitharchitecturekotlin.user.infrastructure

import com.me.injin.testcodewitharchitecturekotlin.common.domain.exception.ResourceNotFoundException
import com.me.injin.testcodewitharchitecturekotlin.user.domain.User
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus
import com.me.injin.testcodewitharchitecturekotlin.user.service.port.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository,
) : UserRepository {
    override fun findById(id: Long): User? {
        return userJpaRepository.findById(id).map(UserEntity::to).orElse(null)
    }

    override fun getById(id: Long): User {
        return findById(id) ?: throw ResourceNotFoundException("User", id.toString())
    }

    override fun findByIdAndStatus(id: Long, userStatus: UserStatus): User? {
        return userJpaRepository.findByIdAndStatus(id, userStatus)?.to()
    }

    override fun findByEmailAndStatus(email: String?, userStatus: UserStatus): User? {
        return userJpaRepository.findByEmailAndStatus(email, userStatus)?.to()
    }

    override fun save(user: User): User {
        return userJpaRepository.save(UserEntity.from(user)).to()
    }

}
