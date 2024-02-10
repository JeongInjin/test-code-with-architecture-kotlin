package com.me.injin.testcodewitharchitecturekotlin.controller

import com.me.injin.testcodewitharchitecturekotlin.model.dto.UserCreateDto
import com.me.injin.testcodewitharchitecturekotlin.model.dto.UserResponse
import com.me.injin.testcodewitharchitecturekotlin.repository.UserEntity
import com.me.injin.testcodewitharchitecturekotlin.service.UserService
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
    fun createUser(@RequestBody userCreateDto: UserCreateDto): ResponseEntity<UserResponse> {
        val userEntity: UserEntity = userService.createUser(userCreateDto)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userController!!.toResponse(userEntity))
    }
}
