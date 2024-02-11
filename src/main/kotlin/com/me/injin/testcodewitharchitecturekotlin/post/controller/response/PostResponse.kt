package com.me.injin.testcodewitharchitecturekotlin.post.controller.response

import com.me.injin.testcodewitharchitecturekotlin.post.domain.Post
import com.me.injin.testcodewitharchitecturekotlin.user.controller.response.UserResponse

class PostResponse(
    var id: Long? = null,
    var content: String? = null,
    var createdAt: Long? = null,
    var modifiedAt: Long? = null,
    var writer: UserResponse? = null,
) {
    companion object {
        fun from(post: Post): PostResponse {
            return PostResponse(
                id = post.id,
                content = post.content,
                createdAt = post.createdAt,
                modifiedAt = post.modifiedAt,
                writer = UserResponse.from(post.writer)
            )
        }
    }
}

