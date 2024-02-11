package com.me.injin.testcodewitharchitecturekotlin.post.service.port

import com.me.injin.testcodewitharchitecturekotlin.post.infrastructure.PostEntity

interface PostRepository {
    fun findById(id: Long): PostEntity?
    fun save(postEntity: PostEntity): PostEntity
}
