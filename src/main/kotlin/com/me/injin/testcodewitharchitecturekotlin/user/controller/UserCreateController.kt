package com.me.injin.testcodewitharchitecturekotlin.user.controller

import com.me.injin.testcodewitharchitecturekotlin.user.controller.response.UserResponse
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserCreate
import com.me.injin.testcodewitharchitecturekotlin.user.infrastructure.UserEntity
import com.me.injin.testcodewitharchitecturekotlin.user.service.UserService
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
    val userController: UserController,
    val userService: UserService,
) {

    @PostMapping
    fun createUser(@RequestBody userCreate: UserCreate): ResponseEntity<UserResponse> {
        val userEntity: UserEntity = userService.create(userCreate)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userController!!.toResponse(userEntity))
    }
}
