package com.me.injin.testcodewitharchitecturekotlin.post.infrastructure

import com.me.injin.testcodewitharchitecturekotlin.post.service.port.PostRepository
import org.springframework.stereotype.Repository

@Repository
class PostRepositoryImpl(
    private val postJpaRepository: PostJpaRepository,
) : PostRepository {
    override fun findById(id: Long): PostEntity? {
        return postJpaRepository.findById(id).orElse(null)
    }

    override fun save(postEntity: PostEntity): PostEntity {
        return postJpaRepository.save(postEntity)
    }
}
