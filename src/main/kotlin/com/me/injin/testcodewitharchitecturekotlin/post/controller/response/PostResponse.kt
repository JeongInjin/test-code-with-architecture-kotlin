package com.me.injin.testcodewitharchitecturekotlin.post.controller.response

import com.me.injin.testcodewitharchitecturekotlin.user.controller.response.UserResponse

class PostResponse(
    var id: Long? = null,
    var content: String? = null,
    var createdAt: Long? = null,
    var modifiedAt: Long? = null,
    var writer: UserResponse? = null,
)

