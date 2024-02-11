package com.me.injin.testcodewitharchitecturekotlin.user.controller.response

import com.me.injin.testcodewitharchitecturekotlin.user.domain.User
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus

data class MyProfileResponse(
    var id: Long? = null,
    var email: String? = null,
    var nickname: String? = null,
    var address: String? = null,
    var status: UserStatus? = null,
    var lastLoginAt: Long? = null,
) {
    companion object {
        fun from(user: User): MyProfileResponse {
            return MyProfileResponse(
                id = user.id,
                email = user.email,
                nickname = user.nickname,
                address = user.address,
                status = user.status,
                lastLoginAt = user.lastLoginAt
            )
        }
    }
}
