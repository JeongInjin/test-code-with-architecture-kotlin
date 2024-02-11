package com.me.injin.testcodewitharchitecturekotlin.user.controller.response

import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus

data class MyProfileResponse(
    var id: Long? = null,
    var email: String? = null,
    var nickname: String? = null,
    var address: String? = null,
    var status: UserStatus? = null,
    var lastLoginAt: Long? = null,
)
