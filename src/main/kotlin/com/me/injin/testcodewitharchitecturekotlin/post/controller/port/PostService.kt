package com.me.injin.testcodewitharchitecturekotlin.post.controller.port

import com.me.injin.testcodewitharchitecturekotlin.post.domain.Post
import com.me.injin.testcodewitharchitecturekotlin.post.domain.PostCreate
import com.me.injin.testcodewitharchitecturekotlin.post.domain.PostUpdate

interface PostService {
    fun getById(id: Long): Post
    fun create(postCreate: PostCreate): Post
    fun update(id: Long, postUpdate: PostUpdate): Post
}
