package com.me.injin.testcodewitharchitecturekotlin.user.infrastructure

import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByIdAndStatus(id: Long, userStatus: UserStatus): UserEntity?

    fun findByEmailAndStatus(email: String?, userStatus: UserStatus): UserEntity?
}
