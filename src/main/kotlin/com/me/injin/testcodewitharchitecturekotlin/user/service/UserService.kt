package com.me.injin.testcodewitharchitecturekotlin.user.service

import com.me.injin.testcodewitharchitecturekotlin.common.domain.exception.ResourceNotFoundException
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
class UserService(
    private val userRepository: UserRepository,
    private val certificationService: CertificationService,
) {

    fun getByEmail(email: String?): User {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
            ?: throw ResourceNotFoundException("Users", email!!)

    }

    fun getById(id: Long): User {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
            ?: throw ResourceNotFoundException("Users", id)
    }

    @Transactional
    fun create(userCreate: UserCreate): User {
        var user = User.from(userCreate)
        user = userRepository.save(user)
        certificationService.send(user.email, user.id!!, user.certificationCode)
        return user
    }

    @Transactional
    fun update(id: Long, userUpdate: UserUpdate): User {
        val user = getById(id)
        user.update(userUpdate)
        return userRepository.save(user)
    }

    @Transactional
    fun login(id: Long) {
        var user = userRepository.findById(id) ?: throw ResourceNotFoundException("User", id.toString())
        user = user.login()
        userRepository.save(user)
    }

    @Transactional
    fun verifyEmail(id: Long, certificationCode: String) {
        var user = userRepository.findById(id) ?: throw ResourceNotFoundException("User", id.toString())
        user = user.certificate(certificationCode)
        userRepository.save(user)
    }
}
