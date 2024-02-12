package com.me.injin.testcodewitharchitecturekotlin.post.domain

import com.me.injin.testcodewitharchitecturekotlin.user.domain.User
import java.time.Clock

data class Post(
    var id: Long? = null,
    var content: String? = null,
    var createdAt: Long? = null,
    var modifiedAt: Long? = null,
    var writer: User,
) {
    companion object {
        fun from(writer: User, postCreate: PostCreate): Post {
            return Post(
                content = postCreate.content,
                writer = writer,
                createdAt = Clock.systemUTC().millis()
            )
        }
    }

    fun update(postUpdate: PostUpdate): Post {
        return Post(
            id = this.id,
            content = postUpdate.content,
            createdAt = this.createdAt,
            modifiedAt = Clock.systemUTC().millis(),
            writer = this.writer
        )
    }

}
