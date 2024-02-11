package com.me.injin.testcodewitharchitecturekotlin.service

import com.me.injin.testcodewitharchitecturekotlin.exception.ResourceNotFoundException
import com.me.injin.testcodewitharchitecturekotlin.model.dto.PostCreateDto
import com.me.injin.testcodewitharchitecturekotlin.model.dto.PostUpdateDto
import com.me.injin.testcodewitharchitecturekotlin.repository.PostEntity
import com.me.injin.testcodewitharchitecturekotlin.repository.PostRepository
import com.me.injin.testcodewitharchitecturekotlin.repository.UserEntity
import org.springframework.stereotype.Service
import java.time.Clock

@Service
class PostService(
    val postRepository: PostRepository,
    val userService: UserService,
) {

    fun getById(id: Long): PostEntity {
        val postEntity: PostEntity? = postRepository.findById(id).orElse(null)
        return postEntity ?: throw ResourceNotFoundException("Posts", id.toString())
    }


    fun create(postCreateDto: PostCreateDto): PostEntity {
        val userEntity: UserEntity = userService.getById(postCreateDto.writerId)
        val postEntity = PostEntity(
            writer = userEntity,
            content = postCreateDto.content,
            createdAt = Clock.systemUTC().millis()
        )
        return postRepository.save(postEntity)
    }

    fun update(id: Long, postUpdateDto: PostUpdateDto): PostEntity {
        val postEntity = getById(id).also { post ->
            post.content = postUpdateDto.content
            post.modifiedAt = Clock.systemUTC().millis()
        }
        return postRepository.save(postEntity)
    }
}
