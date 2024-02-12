package com.me.injin.testcodewitharchitecturekotlin.user.domain

import com.me.injin.testcodewitharchitecturekotlin.common.domain.exception.CertificationCodeNotMatchedException
import com.me.injin.testcodewitharchitecturekotlin.common.service.port.ClockHolder
import com.me.injin.testcodewitharchitecturekotlin.common.service.port.UuidHolder

data class User(
    var id: Long? = null,
    var email: String,
    var nickname: String,
    var address: String,
    var certificationCode: String,
    var status: UserStatus? = UserStatus.PENDING,
    var lastLoginAt: Long? = null,
) {
    companion object {
        fun from(userCreate: UserCreate, uuidHolder: UuidHolder): User {
            return User(
                email = userCreate.email,
                nickname = userCreate.nickname,
                address = userCreate.address,
                certificationCode = uuidHolder.random(),
            )
        }
    }

    fun update(userUpdate: UserUpdate): User {
        this.id = id
        this.email = email
        this.nickname = userUpdate.nickname
        this.address = userUpdate.address
        this.certificationCode = certificationCode
        this.status = status
        this.lastLoginAt = lastLoginAt
        return this
    }

    fun login(clockHolder: ClockHolder): User {
        return User(
            id = id,
            email = email,
            nickname = nickname,
            address = address,
            certificationCode = certificationCode,
            status = status,
            lastLoginAt = clockHolder.millis(),
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
