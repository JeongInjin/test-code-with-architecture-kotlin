package com.me.injin.testcodewitharchitecturekotlin.post.infrastructure

import com.me.injin.testcodewitharchitecturekotlin.post.domain.Post
import com.me.injin.testcodewitharchitecturekotlin.post.service.port.PostRepository
import org.springframework.stereotype.Repository

@Repository
class PostRepositoryImpl(
    private val postJpaRepository: PostJpaRepository,
) : PostRepository {
    override fun findById(id: Long): Post? {
        return postJpaRepository.findById(id).map(PostEntity::to).orElse(null)
    }

    override fun save(post: Post): Post {
        return postJpaRepository.save(PostEntity.from(post)).to()
    }
}
