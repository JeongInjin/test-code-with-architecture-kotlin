package com.me.injin.testcodewitharchitecturekotlin.controller

import com.me.injin.testcodewitharchitecturekotlin.model.dto.MyProfileResponse
import com.me.injin.testcodewitharchitecturekotlin.model.dto.UserResponse
import com.me.injin.testcodewitharchitecturekotlin.model.dto.UserUpdateDto
import com.me.injin.testcodewitharchitecturekotlin.repository.UserEntity
import com.me.injin.testcodewitharchitecturekotlin.service.UserService
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@Tag(name = "유저(users)")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
class UserController(
    val userService: UserService,
) {
    @ResponseStatus
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserResponse> {
        return ResponseEntity
            .ok()
            .body(toResponse(userService.getByIdOrElseThrow(id)))
    }

    @GetMapping("/{id}/verify")
    fun verifyEmail(
        @PathVariable id: Long,
        @RequestParam certificationCode: String,
    ): ResponseEntity<Void> {
        userService.verifyEmail(id, certificationCode)
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create("http://localhost:3000"))
            .build()
    }

    @GetMapping("/me")
    fun getMyInfo(
        @Parameter(
            name = "EMAIL",
            `in` = ParameterIn.HEADER
        ) @RequestHeader("EMAIL") email: String?, // 일반적으로 스프링 시큐리티를 사용한다면 UserPrincipal 에서 가져옵니다.
    ): ResponseEntity<MyProfileResponse> {
        val userEntity: UserEntity = userService.getByEmail(email)
        userService.login(userEntity.id!!)
        return ResponseEntity
            .ok()
            .body(toMyProfileResponse(userEntity))
    }

    @PutMapping("/me")
    @Parameter(`in` = ParameterIn.HEADER, name = "EMAIL")
    fun updateMyInfo(
        @Parameter(
            name = "EMAIL",
            `in` = ParameterIn.HEADER
        ) @RequestHeader("EMAIL") email: String,  // 일반적으로 스프링 시큐리티를 사용한다면 UserPrincipal 에서 가져옵니다.
        @RequestBody userUpdateDto: UserUpdateDto,
    ): ResponseEntity<MyProfileResponse> {
        var userEntity: UserEntity = userService.getByEmail(email)
        userEntity = userService.updateUser(userEntity.id!!, userUpdateDto)
        return ResponseEntity
            .ok()
            .body(toMyProfileResponse(userEntity))
    }

    fun toResponse(userEntity: UserEntity): UserResponse {
        return UserResponse(
            id = userEntity.id,
            email = userEntity.email,
            nickname = userEntity.nickname,
            status = userEntity.status,
            lastLoginAt = userEntity.lastLoginAt
        )
    }

    fun toMyProfileResponse(userEntity: UserEntity): MyProfileResponse {
        val myProfileResponse = MyProfileResponse(
            id = userEntity.id,
            email = userEntity.email,
            nickname = userEntity.nickname,
            status = userEntity.status,
            address = userEntity.address,
            lastLoginAt = userEntity.lastLoginAt
        )
        return myProfileResponse
    }
}
