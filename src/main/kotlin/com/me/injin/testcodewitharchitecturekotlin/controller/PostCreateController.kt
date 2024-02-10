package com.me.injin.testcodewitharchitecturekotlin.controller

import com.me.injin.testcodewitharchitecturekotlin.model.dto.PostCreateDto
import com.me.injin.testcodewitharchitecturekotlin.model.dto.PostResponse
import com.me.injin.testcodewitharchitecturekotlin.service.PostService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "게시물(posts)")
@RestController
@RequestMapping("/api/posts")
class PostCreateController(
    val postService: PostService,
    val postController: PostController,
) {

    @PostMapping
    fun createPost(@RequestBody postCreateDto: PostCreateDto): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postController!!.toResponse(postService.createPost(postCreateDto)))
    }
}
