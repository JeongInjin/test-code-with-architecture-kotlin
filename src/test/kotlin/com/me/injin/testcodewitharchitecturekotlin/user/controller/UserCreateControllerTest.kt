package com.me.injin.testcodewitharchitecturekotlin.user.controller

import com.me.injin.testcodewitharchitecturekotlin.mock.TestContainer
import com.me.injin.testcodewitharchitecturekotlin.mock.TestUuidHolder
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserCreate
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatusCode

class UserCreateControllerTest {

    @Test
    fun `사용자는 회원 가입을 할 수 있고 회원가입된 사용자는 PENDING 상태이다`() {
        val testContainer = TestContainer(uuidHolder = TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"))
        // given
        val userCreate = UserCreate(
            email = "injin.dev@gmail.com",
            nickname = "injin",
            address = "Pangyo"
        )

        // when
        val result = testContainer.userCreateController.createUser(userCreate)

        // then
        assertThat(result.statusCode).isEqualTo(HttpStatusCode.valueOf(201))
        assertThat(result.body).isNotNull()
        assertThat(result.body?.email).isEqualTo("injin.dev@gmail.com")
        assertThat(result.body?.nickname).isEqualTo("injin")
        assertThat(result.body?.lastLoginAt).isNull()
        assertThat(result.body?.status).isEqualTo(UserStatus.PENDING)
        assertThat(testContainer.userRepository.getById(1).certificationCode).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
    }
}
