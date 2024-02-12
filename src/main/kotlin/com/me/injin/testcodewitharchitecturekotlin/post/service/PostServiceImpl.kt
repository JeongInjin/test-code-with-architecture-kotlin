package com.me.injin.testcodewitharchitecturekotlin.post.service

import com.me.injin.testcodewitharchitecturekotlin.common.domain.exception.ResourceNotFoundException
import com.me.injin.testcodewitharchitecturekotlin.common.service.port.ClockHolder
import com.me.injin.testcodewitharchitecturekotlin.post.controller.port.PostService
import com.me.injin.testcodewitharchitecturekotlin.post.domain.Post
import com.me.injin.testcodewitharchitecturekotlin.post.domain.PostCreate
import com.me.injin.testcodewitharchitecturekotlin.post.domain.PostUpdate
import com.me.injin.testcodewitharchitecturekotlin.post.service.port.PostRepository
import com.me.injin.testcodewitharchitecturekotlin.user.domain.User
import com.me.injin.testcodewitharchitecturekotlin.user.service.port.UserRepository
import org.springframework.stereotype.Service

@Service
class PostServiceImpl(
    val postRepository: PostRepository,
    val userRepository: UserRepository,
    val clockHolder: ClockHolder,
) : PostService {

    override fun getById(id: Long): Post {
        val post: Post? = postRepository.findById(id)
        return post ?: throw ResourceNotFoundException("Posts", id.toString())
    }


    override fun create(postCreate: PostCreate): Post {
        val writer: User = userRepository.getById(postCreate.writerId)
        val post = Post.from(writer, postCreate, clockHolder)
        return postRepository.save(post)
    }

    override fun update(id: Long, postUpdate: PostUpdate): Post {
        var post = getById(id)
        post = post.update(postUpdate, clockHolder)
        return postRepository.save(post)
    }
}
