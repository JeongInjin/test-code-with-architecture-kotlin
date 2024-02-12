package com.me.injin.testcodewitharchitecturekotlin.user.controller

import com.me.injin.testcodewitharchitecturekotlin.common.domain.exception.CertificationCodeNotMatchedException
import com.me.injin.testcodewitharchitecturekotlin.mock.TestClockHolder
import com.me.injin.testcodewitharchitecturekotlin.mock.TestContainer
import com.me.injin.testcodewitharchitecturekotlin.user.domain.User
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserUpdate
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class UserControllerTest {

    @Test
    fun `사용자는 특정 유저의 정보를 개인정보는 소거된채 전달 받을 수 있다`() {
        // given
        val testContainer = TestContainer()
        testContainer.userRepository.save(
            User(
                id = 1L,
                email = "injin.dev@gmail.com",
                nickname = "injin",
                status = UserStatus.ACTIVE,
                lastLoginAt = 123123L,
                address = "Seoul",
                certificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"
            )
        )
        // when
        val result = testContainer.userController.getUserById(1L)

        // then
        assertThat(result.statusCode).isEqualTo(HttpStatus.valueOf(200))
        assertThat(result.body).isNotNull()
        assertThat(result.body!!.email).isEqualTo("injin.dev@gmail.com")
        assertThat(result.body!!.nickname).isEqualTo("injin")
        assertThat(result.body!!.lastLoginAt).isEqualTo(123123)
        assertThat(result.body!!.status).isEqualTo(UserStatus.ACTIVE)
    }

    @Test
    fun `사용자는 존재하지 않는 유저의 아이디로 api 호출할 경우 404 응답을 받는다`() {
        // given
        val testContainer = TestContainer()
        // when
        // then
        assertThatThrownBy { testContainer.userController.getUserById(1L) }
            .isInstanceOf(RuntimeException::class.java)
    }

    @Test
    fun `사용자는 인증 코드로 계정을 활성화 시킬 수 있다`() {
        // given
        val testContainer = TestContainer()
        testContainer.userRepository.save(
            User(
                id = 1L,
                email = "injin.dev@gmail.com",
                nickname = "injin",
                status = UserStatus.PENDING,
                lastLoginAt = 0L,
                address = "Seoul",
                certificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"
            )
        )

        // when
        val result = testContainer.userController.verifyEmail(1L, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")

        // then
        assertThat(result.statusCode).isEqualTo(HttpStatus.valueOf(302))

        assertThat(testContainer.userRepository.findById(1L)?.status).isEqualTo(UserStatus.ACTIVE)
    }

    @Test
    fun `사용자는 인증 코드가 일치하지 않을 경우 권한 없음 에러를 내려준다`() {
        val testContainer = TestContainer()
        testContainer.userRepository.save(
            User(
                id = 1L,
                email = "injin.dev@gmail.com",
                nickname = "injin",
                status = UserStatus.PENDING,
                lastLoginAt = 0L,
                address = "Seoul",
                certificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"
            )
        )

        // when
        // then
        assertThatThrownBy {
            testContainer.userController.verifyEmail(1, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac")
        }.isInstanceOf(CertificationCodeNotMatchedException::class.java)
    }

    @Test
    fun `사용자는 내 정보를 불러올 때 개인정보인 주소도 갖고 올 수 있다`() {
        // given
        val testContainer = TestContainer(clockHolder = TestClockHolder(1678530673958L))
        testContainer.userRepository.save(
            User(
                id = 1L,
                email = "injin.dev@gmail.com",
                nickname = "injin",
                status = UserStatus.ACTIVE,
                lastLoginAt = 123L,
                address = "Seoul",
                certificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"
            )
        )

        // when
        val result = testContainer.userController.getMyInfo("injin.dev@gmail.com")
        // then
        assertThat(result.statusCode).isEqualTo(HttpStatus.valueOf(200))
        assertThat(result.body).isNotNull()
        assertThat(result.body?.email).isEqualTo("injin.dev@gmail.com")
        assertThat(result.body?.nickname).isEqualTo("injin")
        assertThat(result.body?.lastLoginAt).isEqualTo(1678530673958L)
        assertThat(result.body?.address).isEqualTo("Seoul")
        assertThat(result.body?.status).isEqualTo(UserStatus.ACTIVE)
    }

    @Test
    fun `사용자는 내 정보를 수정할 수 있다`() {
        // given
        val testContainer = TestContainer()
        testContainer.userRepository.save(
            User(
                id = 1L,
                email = "injin.dev@gmail.com",
                nickname = "injin",
                status = UserStatus.ACTIVE,
                lastLoginAt = 1678530673958L,
                address = "Seoul",
                certificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"
            )
        )

        // when
        val result = testContainer.userController.updateMyInfo(
            "injin.dev@gmail.com",
            UserUpdate(nickname = "injin-p", address = "Pohang")
        )

        // then
        assertThat(result.statusCode).isEqualTo(HttpStatus.valueOf(200))
        assertThat(result.body).isNotNull()
        assertThat(result.body?.email).isEqualTo("injin.dev@gmail.com")
        assertThat(result.body?.nickname).isEqualTo("injin-p")
        assertThat(result.body?.lastLoginAt).isEqualTo(1678530673958L)
        assertThat(result.body?.address).isEqualTo("Pohang")
        assertThat(result.body?.status).isEqualTo(UserStatus.ACTIVE)
    }

}
