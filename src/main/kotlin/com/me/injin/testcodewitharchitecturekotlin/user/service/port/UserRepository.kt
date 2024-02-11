package com.me.injin.testcodewitharchitecturekotlin.user.service.port

import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus
import com.me.injin.testcodewitharchitecturekotlin.user.infrastructure.UserEntity

interface UserRepository {
    fun findByIdAndStatus(id: Long, userStatus: UserStatus): UserEntity?

    fun findByEmailAndStatus(email: String?, userStatus: UserStatus): UserEntity?
    fun save(userEntity: UserEntity): UserEntity
    fun findById(id: Long): UserEntity?

}
