package com.me.injin.testcodewitharchitecturekotlin.post.controller

import com.me.injin.testcodewitharchitecturekotlin.post.controller.port.PostService
import com.me.injin.testcodewitharchitecturekotlin.post.controller.response.PostResponse
import com.me.injin.testcodewitharchitecturekotlin.post.domain.PostUpdate
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "게시물(posts)")
@RestController
@RequestMapping("/api/posts")
class PostController(
    val postService: PostService,
) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<PostResponse> {
        return ResponseEntity
            .ok()
            .body(PostResponse.from(postService.getById(id)))
    }

    @PutMapping("/{id}")
    fun updatePost(@PathVariable id: Long, @RequestBody postUpdate: PostUpdate): ResponseEntity<PostResponse> {
        return ResponseEntity
            .ok()
            .body(PostResponse.from(postService.update(id, postUpdate)))
    }

}
