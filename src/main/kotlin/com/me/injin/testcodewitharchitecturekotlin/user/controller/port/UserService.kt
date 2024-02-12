package com.me.injin.testcodewitharchitecturekotlin.user.controller.port

import com.me.injin.testcodewitharchitecturekotlin.user.domain.User
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserCreate
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserUpdate

interface UserService {
    fun getByEmail(email: String?): User
    fun getById(id: Long): User
    fun create(userCreate: UserCreate): User
    fun update(id: Long, userUpdate: UserUpdate): User
    fun login(id: Long)
    fun verifyEmail(id: Long, certificationCode: String)
}
