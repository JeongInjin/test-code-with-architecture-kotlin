package com.me.injin.testcodewitharchitecturekotlin.user.controller

import com.me.injin.testcodewitharchitecturekotlin.user.controller.port.UserService
import com.me.injin.testcodewitharchitecturekotlin.user.controller.response.MyProfileResponse
import com.me.injin.testcodewitharchitecturekotlin.user.controller.response.UserResponse
import com.me.injin.testcodewitharchitecturekotlin.user.domain.User
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserUpdate
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
    fun getById(@PathVariable id: Long): ResponseEntity<UserResponse> {
        return ResponseEntity
            .ok()
            .body(UserResponse.from(userService.getById(id)))
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
        var user: User = userService.getByEmail(email)
        userService.login(user.id!!)
        user = userService.getByEmail(email)
        return ResponseEntity
            .ok()
            .body(MyProfileResponse.from(user))
    }

    @PutMapping("/me")
    @Parameter(`in` = ParameterIn.HEADER, name = "EMAIL")
    fun updateMyInfo(
        @Parameter(
            name = "EMAIL",
            `in` = ParameterIn.HEADER
        ) @RequestHeader("EMAIL") email: String,  // 일반적으로 스프링 시큐리티를 사용한다면 UserPrincipal 에서 가져옵니다.
        @RequestBody userUpdate: UserUpdate,
    ): ResponseEntity<MyProfileResponse> {
        var user = userService.getByEmail(email)
        user = userService.update(user.id!!, userUpdate)
        return ResponseEntity
            .ok()
            .body(MyProfileResponse.from(user))
    }

}
