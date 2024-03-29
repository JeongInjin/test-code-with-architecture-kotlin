package com.me.injin.testcodewitharchitecturekotlin.user.service

import com.me.injin.testcodewitharchitecturekotlin.common.domain.exception.ResourceNotFoundException
import com.me.injin.testcodewitharchitecturekotlin.common.service.port.ClockHolder
import com.me.injin.testcodewitharchitecturekotlin.common.service.port.UuidHolder
import com.me.injin.testcodewitharchitecturekotlin.user.controller.port.UserService
import com.me.injin.testcodewitharchitecturekotlin.user.domain.User
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserCreate
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserUpdate
import com.me.injin.testcodewitharchitecturekotlin.user.service.port.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val certificationService: CertificationService,
    private val uuidHolder: UuidHolder,
    private val clockHolder: ClockHolder,
) : UserService {

    override fun getByEmail(email: String?): User {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
            ?: throw ResourceNotFoundException("Users", email!!)

    }

    override fun getById(id: Long): User {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
            ?: throw ResourceNotFoundException("Users", id)
    }

    @Transactional
    override fun create(userCreate: UserCreate): User {
        var user = User.from(userCreate, uuidHolder)
        user = userRepository.save(user)
        certificationService.send(user.email, user.id!!, user.certificationCode)
        return user
    }

    @Transactional
    override fun update(id: Long, userUpdate: UserUpdate): User {
        val user = getById(id)
        user.update(userUpdate)
        return userRepository.save(user)
    }

    @Transactional
    override fun login(id: Long) {
        var user = userRepository.findById(id) ?: throw ResourceNotFoundException("User", id.toString())
        user = user.login(clockHolder)
        userRepository.save(user)
    }

    @Transactional
    override fun verifyEmail(id: Long, certificationCode: String) {
        var user = userRepository.findById(id) ?: throw ResourceNotFoundException("User", id.toString())
        user = user.certificate(certificationCode)
        userRepository.save(user)
    }
}
