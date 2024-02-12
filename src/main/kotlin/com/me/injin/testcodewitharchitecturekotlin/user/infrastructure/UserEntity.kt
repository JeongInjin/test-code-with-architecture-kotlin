package com.me.injin.testcodewitharchitecturekotlin.user.infrastructure

import com.me.injin.testcodewitharchitecturekotlin.user.domain.User
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus
import jakarta.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "email")
    var email: String,

    @Column(name = "nickname")
    var nickname: String,

    @Column(name = "address")
    var address: String,

    @Column(name = "certification_code")
    var certificationCode: String,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: UserStatus? = UserStatus.PENDING,

    @Column(name = "last_login_at")
    var lastLoginAt: Long? = null,
) {
    companion object {
        fun from(user: User): UserEntity {
            return UserEntity(
                id = user.id,
                email = user.email,
                nickname = user.nickname,
                address = user.address,
                certificationCode = user.certificationCode,
                status = user.status,
                lastLoginAt = user.lastLoginAt,
            )
        }
    }

    fun toModel(): User {
        return User(
            id = id,
            email = email,
            nickname = nickname,
            address = address,
            certificationCode = certificationCode,
            status = status,
            lastLoginAt = lastLoginAt,
        )
    }
}
