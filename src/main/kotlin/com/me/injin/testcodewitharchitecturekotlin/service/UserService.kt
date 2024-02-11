package com.me.injin.testcodewitharchitecturekotlin.service

import com.me.injin.testcodewitharchitecturekotlin.exception.CertificationCodeNotMatchedException
import com.me.injin.testcodewitharchitecturekotlin.exception.ResourceNotFoundException
import com.me.injin.testcodewitharchitecturekotlin.model.UserStatus
import com.me.injin.testcodewitharchitecturekotlin.model.dto.UserCreateDto
import com.me.injin.testcodewitharchitecturekotlin.model.dto.UserUpdateDto
import com.me.injin.testcodewitharchitecturekotlin.repository.UserEntity
import com.me.injin.testcodewitharchitecturekotlin.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Clock
import java.util.*

@Service
@RequiredArgsConstructor
class UserService(
    val userRepository: UserRepository,
    val mailSender: JavaMailSender,
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
    fun create(userCreateDto: UserCreateDto): UserEntity {
        var userEntity = UserEntity(
            email = userCreateDto.email,
            nickname = userCreateDto.nickname,
            address = userCreateDto.address,
            status = UserStatus.PENDING,
            certificationCode = UUID.randomUUID().toString()
        )
        userEntity = userRepository.save(userEntity)
        val certificationUrl = generateCertificationUrl(userEntity)
        println("certificationUrl: $certificationUrl")
        sendCertificationEmail(userCreateDto.email, certificationUrl)
        return userEntity
    }

    @Transactional
    fun update(id: Long, userUpdateDto: UserUpdateDto): UserEntity {
        val userEntity = getById(id)
        userEntity.apply {
            nickname = userUpdateDto.nickname
            address = userUpdateDto.address
        }
        return userRepository.save(userEntity)
    }

    @Transactional
    fun login(id: Long) {
        userRepository.findById(id).orElseThrow {
            ResourceNotFoundException("User", id.toString())
        }?.also { userEntity ->
            userEntity.lastLoginAt = Clock.systemUTC().millis()
        }
    }

    @Transactional
    fun verifyEmail(id: Long, certificationCode: String) {
        userRepository.findById(id).orElseThrow {
            ResourceNotFoundException("User", id.toString())
        }?.also { userEntity ->
            if (certificationCode != userEntity.certificationCode) {
                throw CertificationCodeNotMatchedException()
            }
            userEntity.status = UserStatus.ACTIVE
        }
    }

    private fun sendCertificationEmail(email: String, certificationUrl: String) {
        val message = SimpleMailMessage().apply {
            setTo(email)
            subject = "Please certify your email address"
            text = "Please click the following link to certify your email address: $certificationUrl"
        }
        try {
            mailSender.send(message)
        } catch (e: Exception) {
        }
    }

    private fun generateCertificationUrl(userEntity: UserEntity): String =
        "http://localhost:8080/api/users/${userEntity.id}/verify?certificationCode=${userEntity.certificationCode}"
}
