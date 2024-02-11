package com.me.injin.testcodewitharchitecturekotlin.post.service.port

import com.me.injin.testcodewitharchitecturekotlin.post.domain.Post

interface PostRepository {
    fun findById(id: Long): Post?
    fun save(post: Post): Post
}
