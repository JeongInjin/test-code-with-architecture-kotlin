package com.me.injin.testcodewitharchitecturekotlin.post.domain

import com.me.injin.testcodewitharchitecturekotlin.common.service.port.ClockHolder
import com.me.injin.testcodewitharchitecturekotlin.user.domain.User

data class Post(
    var id: Long? = null,
    var content: String? = null,
    var createdAt: Long? = null,
    var modifiedAt: Long? = null,
    var writer: User,
) {
    companion object {
        fun from(writer: User, postCreate: PostCreate, clockHolder: ClockHolder): Post {
            return Post(
                content = postCreate.content,
                writer = writer,
                createdAt = clockHolder.millis()
            )
        }
    }

    fun update(postUpdate: PostUpdate, clockHolder: ClockHolder): Post {
        return Post(
            id = this.id,
            content = postUpdate.content,
            createdAt = this.createdAt,
            modifiedAt = clockHolder.millis(),
            writer = this.writer
        )
    }

}
