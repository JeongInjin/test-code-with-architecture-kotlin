package com.me.injin.testcodewitharchitecturekotlin.user.controller.response

import com.me.injin.testcodewitharchitecturekotlin.user.domain.User
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class UserResponseTest {

    @Test
    fun `User 의 응답을 생성할 수 있다`() {
        //given
        val user = User(
            id = 1L,
            email = "injin.dev@gmail.com",
            nickname = "injin",
            address = "Seoul",
            certificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab",
            status = UserStatus.ACTIVE,
            lastLoginAt = 100L
        )

        //when
        val myProfileResponse = UserResponse.from(user)

        //then
        //then
        Assertions.assertThat(myProfileResponse.id).isEqualTo(1)
        Assertions.assertThat(myProfileResponse.email).isEqualTo("injin.dev@gmail.com")
        Assertions.assertThat(myProfileResponse.nickname).isEqualTo("injin")
        Assertions.assertThat(myProfileResponse.status).isEqualTo(UserStatus.ACTIVE)
        Assertions.assertThat(myProfileResponse.lastLoginAt).isEqualTo(100L)

    }

}
