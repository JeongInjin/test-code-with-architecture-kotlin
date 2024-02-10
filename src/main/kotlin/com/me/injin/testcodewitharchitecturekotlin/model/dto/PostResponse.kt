package com.me.injin.testcodewitharchitecturekotlin.model.dto

class PostResponse(
    var id: Long? = null,
    var content: String? = null,
    var createdAt: Long? = null,
    var modifiedAt: Long? = null,
    var writer: UserResponse? = null,
)

