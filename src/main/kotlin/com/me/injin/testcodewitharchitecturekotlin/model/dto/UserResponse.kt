package com.me.injin.testcodewitharchitecturekotlin.model.dto

import com.me.injin.testcodewitharchitecturekotlin.model.UserStatus

data class UserResponse(
    val id: Long? = null,
    val email: String? = null,
    val nickname: String? = null,
    val status: UserStatus? = null,
    val lastLoginAt: Long? = null,
)
