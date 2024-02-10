package com.me.injin.testcodewitharchitecturekotlin.repository

import com.me.injin.testcodewitharchitecturekotlin.model.UserStatus
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
)
