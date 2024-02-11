package com.me.injin.testcodewitharchitecturekotlin.post.infrastructure

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
)
