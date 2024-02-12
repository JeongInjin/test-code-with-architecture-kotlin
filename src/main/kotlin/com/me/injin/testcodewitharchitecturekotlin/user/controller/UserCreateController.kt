package com.me.injin.testcodewitharchitecturekotlin.user.controller

import com.me.injin.testcodewitharchitecturekotlin.user.controller.port.UserService
import com.me.injin.testcodewitharchitecturekotlin.user.controller.response.UserResponse
import com.me.injin.testcodewitharchitecturekotlin.user.domain.User
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserCreate
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "유저(users)")
@RestController
@RequestMapping("/api/users")
class UserCreateController(
    val userService: UserService,
) {

    @PostMapping
    fun create(@RequestBody userCreate: UserCreate): ResponseEntity<UserResponse> {
        val user: User = userService.create(userCreate)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(UserResponse.from(user))
    }
}
