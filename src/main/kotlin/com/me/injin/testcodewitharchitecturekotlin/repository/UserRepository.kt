package com.me.injin.testcodewitharchitecturekotlin.repository

import com.me.injin.testcodewitharchitecturekotlin.model.UserStatus
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByIdAndStatus(id: Long, userStatus: UserStatus): UserEntity?

    fun findByEmailAndStatus(email: String?, userStatus: UserStatus): UserEntity?
}
