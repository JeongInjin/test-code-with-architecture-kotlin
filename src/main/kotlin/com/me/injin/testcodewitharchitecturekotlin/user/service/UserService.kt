package com.me.injin.testcodewitharchitecturekotlin.user.service

import com.me.injin.testcodewitharchitecturekotlin.common.domain.exception.CertificationCodeNotMatchedException
import com.me.injin.testcodewitharchitecturekotlin.common.domain.exception.ResourceNotFoundException
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserCreate
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserUpdate
import com.me.injin.testcodewitharchitecturekotlin.user.infrastructure.UserEntity
import com.me.injin.testcodewitharchitecturekotlin.user.service.port.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Clock
import java.util.*

@Service
@RequiredArgsConstructor
class UserService(
    private val userRepository: UserRepository,
    private val certificationService: CertificationService,
) {

    fun getByEmail(email: String?): UserEntity {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
            ?: throw ResourceNotFoundException("Users", email!!)

    }

    fun getById(id: Long): UserEntity {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
            ?: throw ResourceNotFoundException("Users", id)
    }

    @Transactional
    fun create(userCreate: UserCreate): UserEntity {
        var userEntity = UserEntity(
            email = userCreate.email,
            nickname = userCreate.nickname,
            address = userCreate.address,
            status = UserStatus.PENDING,
            certificationCode = UUID.randomUUID().toString()
        )
        userEntity = userRepository.save(userEntity)
        certificationService.send(userEntity.email, userEntity.id!!, userEntity.certificationCode)
        return userEntity
    }

    @Transactional
    fun update(id: Long, userUpdate: UserUpdate): UserEntity {
        val userEntity = getById(id)
        userEntity.apply {
            nickname = userUpdate.nickname
            address = userUpdate.address
        }
        return userRepository.save(userEntity)
    }

    @Transactional
    fun login(id: Long) {
        userRepository.findById(id).also { userEntity ->
            userEntity?.lastLoginAt = Clock.systemUTC().millis()
        } ?: throw ResourceNotFoundException("User", id.toString())
    }

    @Transactional
    fun verifyEmail(id: Long, certificationCode: String) {
        userRepository.findById(id).also { userEntity ->
            if (certificationCode != userEntity!!.certificationCode) {
                throw CertificationCodeNotMatchedException()
            }
            userEntity.status = UserStatus.ACTIVE
        } ?: throw ResourceNotFoundException("User", id.toString())
    }
}
