package com.me.injin.testcodewitharchitecturekotlin.post.controller

import com.me.injin.testcodewitharchitecturekotlin.post.controller.response.PostResponse
import com.me.injin.testcodewitharchitecturekotlin.post.domain.PostUpdate
import com.me.injin.testcodewitharchitecturekotlin.post.infrastructure.PostEntity
import com.me.injin.testcodewitharchitecturekotlin.post.service.PostService
import com.me.injin.testcodewitharchitecturekotlin.user.controller.UserController
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "게시물(posts)")
@RestController
@RequestMapping("/api/posts")
class PostController(
    val postService: PostService,
    val userController: UserController,
) {

    @GetMapping("/{id}")
    fun getPostById(@PathVariable id: Long): ResponseEntity<PostResponse> {
        return ResponseEntity
            .ok()
            .body(toResponse(postService.getById(id)))
    }

    @PutMapping("/{id}")
    fun updatePost(@PathVariable id: Long, @RequestBody postUpdate: PostUpdate): ResponseEntity<PostResponse> {
        return ResponseEntity
            .ok()
            .body(toResponse(postService.update(id, postUpdate)))
    }

    fun toResponse(postEntity: PostEntity): PostResponse {
        return PostResponse(
            id = postEntity.id,
            content = postEntity.content,
            createdAt = postEntity.createdAt,
            modifiedAt = postEntity.modifiedAt,
            writer = userController.toResponse(postEntity.writer)
        )
    }
}
