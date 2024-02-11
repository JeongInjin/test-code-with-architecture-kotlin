package com.me.injin.testcodewitharchitecturekotlin.post.service

import com.me.injin.testcodewitharchitecturekotlin.common.domain.exception.ResourceNotFoundException
import com.me.injin.testcodewitharchitecturekotlin.post.domain.Post
import com.me.injin.testcodewitharchitecturekotlin.post.domain.PostCreate
import com.me.injin.testcodewitharchitecturekotlin.post.domain.PostUpdate
import com.me.injin.testcodewitharchitecturekotlin.post.service.port.PostRepository
import com.me.injin.testcodewitharchitecturekotlin.user.domain.User
import com.me.injin.testcodewitharchitecturekotlin.user.service.UserService
import org.springframework.stereotype.Service

@Service
class PostService(
    val postRepository: PostRepository,
    val userService: UserService,
) {

    fun getById(id: Long): Post {
        val post: Post? = postRepository.findById(id)
        return post ?: throw ResourceNotFoundException("Posts", id.toString())
    }


    fun create(postCreate: PostCreate): Post {
        val writer: User = userService.getById(postCreate.writerId)
        val post = Post.from(writer, postCreate)
        return postRepository.save(post)
    }

    fun update(id: Long, postUpdate: PostUpdate): Post {
        var post = getById(id)
        post = post.update(postUpdate)
        return postRepository.save(post)
    }
}
