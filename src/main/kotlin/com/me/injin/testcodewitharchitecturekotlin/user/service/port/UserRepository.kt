package com.me.injin.testcodewitharchitecturekotlin.user.service.port

import com.me.injin.testcodewitharchitecturekotlin.user.domain.User
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus

interface UserRepository {
    fun findByIdAndStatus(id: Long, userStatus: UserStatus): User?
    fun findByEmailAndStatus(email: String?, userStatus: UserStatus): User?
    fun save(user: User): User
    fun findById(id: Long): User?

}
