package com.me.injin.testcodewitharchitecturekotlin.controller

import com.me.injin.testcodewitharchitecturekotlin.model.dto.PostResponse
import com.me.injin.testcodewitharchitecturekotlin.model.dto.PostUpdateDto
import com.me.injin.testcodewitharchitecturekotlin.repository.PostEntity
import com.me.injin.testcodewitharchitecturekotlin.service.PostService
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
            .body(toResponse(postService.getPostById(id)))
    }

    @PutMapping("/{id}")
    fun updatePost(@PathVariable id: Long, @RequestBody postUpdateDto: PostUpdateDto): ResponseEntity<PostResponse> {
        return ResponseEntity
            .ok()
            .body(toResponse(postService.updatePost(id, postUpdateDto)))
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
