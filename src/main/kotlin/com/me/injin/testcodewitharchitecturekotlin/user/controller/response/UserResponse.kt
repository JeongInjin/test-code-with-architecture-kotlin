package com.me.injin.testcodewitharchitecturekotlin.user.controller.response

import com.me.injin.testcodewitharchitecturekotlin.user.domain.User
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus

data class UserResponse(
    val id: Long? = null,
    val email: String? = null,
    val nickname: String? = null,
    val status: UserStatus? = null,
    val lastLoginAt: Long? = null,
) {
    companion object {
        fun from(user: User): UserResponse {
            return UserResponse(
                id = user.id,
                email = user.email,
                nickname = user.nickname,
                status = user.status,
                lastLoginAt = user.lastLoginAt
            )
        }
    }
}
