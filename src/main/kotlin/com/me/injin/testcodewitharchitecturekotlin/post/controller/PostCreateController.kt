package com.me.injin.testcodewitharchitecturekotlin.post.controller

import com.me.injin.testcodewitharchitecturekotlin.post.controller.port.PostService
import com.me.injin.testcodewitharchitecturekotlin.post.controller.response.PostResponse
import com.me.injin.testcodewitharchitecturekotlin.post.domain.PostCreate
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
) {

    @PostMapping
    fun create(@RequestBody postCreate: PostCreate): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(PostResponse.from(postService.create(postCreate)))
    }
}
