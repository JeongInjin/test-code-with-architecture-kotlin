package com.me.injin.testcodewitharchitecturekotlin.user.domain

import com.me.injin.testcodewitharchitecturekotlin.common.domain.exception.CertificationCodeNotMatchedException
import java.time.Clock
import java.util.*

class User(
    val id: Long? = null,
    val email: String,
    var nickname: String,
    var address: String,
    val certificationCode: String,
    var status: UserStatus? = UserStatus.PENDING,
    var lastLoginAt: Long? = null,
) {
    companion object {
        fun from(userCreate: UserCreate): User {
            return User(
                email = userCreate.email,
                nickname = userCreate.nickname,
                address = userCreate.address,
                certificationCode = UUID.randomUUID().toString(),
            )
        }
    }

    fun update(userUpdate: UserUpdate) {
        this.nickname = userUpdate.nickname
        this.address = userUpdate.address
    }

    fun login(): User {
        return User(
            id = id,
            email = email,
            nickname = nickname,
            address = address,
            certificationCode = certificationCode,
            status = status,
            lastLoginAt = Clock.systemUTC().millis(),
        )
    }

    fun certificate(certificationCode: String): User {
        if (this.certificationCode != certificationCode) {
            throw CertificationCodeNotMatchedException()
        }
        return User(
            id = id,
            email = email,
            nickname = nickname,
            address = address,
            certificationCode = certificationCode,
            status = UserStatus.ACTIVE,
            lastLoginAt = lastLoginAt,
        )
    }
}
