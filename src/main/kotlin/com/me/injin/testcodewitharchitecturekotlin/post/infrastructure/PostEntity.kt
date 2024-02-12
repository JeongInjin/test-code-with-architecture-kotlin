package com.me.injin.testcodewitharchitecturekotlin.post.infrastructure

import com.me.injin.testcodewitharchitecturekotlin.post.domain.Post
import com.me.injin.testcodewitharchitecturekotlin.user.infrastructure.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "posts")
class PostEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "content")
    var content: String? = null,

    @Column(name = "created_at")
    var createdAt: Long? = null,

    @Column(name = "modified_at")
    var modifiedAt: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var writer: UserEntity,
) {
    companion object {
        fun from(post: Post): PostEntity {
            return PostEntity(
                id = post.id,
                content = post.content,
                createdAt = post.createdAt,
                modifiedAt = post.modifiedAt,
                writer = UserEntity.from(post.writer),
            )
        }
    }

    fun toModel(): Post {
        return Post(
            id = id,
            content = content,
            createdAt = createdAt,
            modifiedAt = modifiedAt,
            writer = writer.toModel(),
        )
    }
}
