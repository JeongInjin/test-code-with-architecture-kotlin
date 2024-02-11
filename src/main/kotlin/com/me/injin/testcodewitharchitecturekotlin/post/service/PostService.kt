package com.me.injin.testcodewitharchitecturekotlin.post.service

import com.me.injin.testcodewitharchitecturekotlin.common.domain.exception.ResourceNotFoundException
import com.me.injin.testcodewitharchitecturekotlin.post.domain.PostCreate
import com.me.injin.testcodewitharchitecturekotlin.post.domain.PostUpdate
import com.me.injin.testcodewitharchitecturekotlin.post.infrastructure.PostEntity
import com.me.injin.testcodewitharchitecturekotlin.post.service.port.PostRepository
import com.me.injin.testcodewitharchitecturekotlin.user.infrastructure.UserEntity
import com.me.injin.testcodewitharchitecturekotlin.user.service.UserService
import org.springframework.stereotype.Service
import java.time.Clock

@Service
class PostService(
    val postRepository: PostRepository,
    val userService: UserService,
) {

    fun getById(id: Long): PostEntity {
        val postEntity: PostEntity? = postRepository.findById(id)
        return postEntity ?: throw ResourceNotFoundException("Posts", id.toString())
    }


    fun create(postCreate: PostCreate): PostEntity {
        val userEntity: UserEntity = userService.getById(postCreate.writerId)
        val postEntity = PostEntity(
            writer = userEntity,
            content = postCreate.content,
            createdAt = Clock.systemUTC().millis()
        )
        return postRepository.save(postEntity)
    }

    fun update(id: Long, postUpdate: PostUpdate): PostEntity {
        val postEntity = getById(id).also { post ->
            post.content = postUpdate.content
            post.modifiedAt = Clock.systemUTC().millis()
        }
        return postRepository.save(postEntity)
    }
}
