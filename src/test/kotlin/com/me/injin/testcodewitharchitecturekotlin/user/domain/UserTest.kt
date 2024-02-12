package com.me.injin.testcodewitharchitecturekotlin.user.domain

import com.me.injin.testcodewitharchitecturekotlin.common.domain.exception.CertificationCodeNotMatchedException
import com.me.injin.testcodewitharchitecturekotlin.mock.TestClockHolder
import com.me.injin.testcodewitharchitecturekotlin.mock.TestUuidHolder
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class UserTest {

    @Test
    fun `UserCreate 객체로 생성할 수 있다`() {
        //given
        val userCreate = UserCreate(
            email = "injin.dev.test@gmail.com",
            nickname = "injin-t",
            address = "Gyeongi-t",
        )

        //when
        val user = User.from(userCreate, TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"))

        //then
        assertThat(user.id).isNull()
        assertThat(user.email).isEqualTo("injin.dev.test@gmail.com")
        assertThat(user.nickname).isEqualTo("injin-t")
        assertThat(user.address).isEqualTo("Gyeongi-t")
        assertThat(user.status).isEqualTo(UserStatus.PENDING)
        assertThat(user.certificationCode).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
    }

    @Test
    fun `UserUpdate 객체로 업데이트할 수 있다`() {
        //given
        var user = User(
            id = 1L,
            email = "injin.dev@gmail.com",
            nickname = "injin",
            status = UserStatus.PENDING,
            lastLoginAt = 120L,
            address = "Seoul",
            certificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"
        )
        val userUpdate = UserUpdate(
            nickname = "injin-p",
            address = "Pohang",
        )

        //when
        user = user.update(userUpdate)

        //then
        assertThat(user.id).isEqualTo(1L)
        assertThat(user.email).isEqualTo("injin.dev@gmail.com")
        assertThat(user.nickname).isEqualTo("injin-p")
        assertThat(user.address).isEqualTo("Pohang")
        assertThat(user.status).isEqualTo(UserStatus.PENDING)
        assertThat(user.certificationCode).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
        assertThat(user.lastLoginAt).isEqualTo(120L)
    }

    @Test
    fun `로그인할 수 있고 로그인 시 마지막 로그인 시간이 변경된다`() {
        //given
        var user = User(
            id = 1L,
            email = "injin.dev@gmail.com",
            nickname = "injin",
            status = UserStatus.ACTIVE,
            lastLoginAt = 120L,
            address = "Seoul",
            certificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"
        )

        //when
        user = user.login(TestClockHolder(12341235L))

        //then
        assertThat(user.lastLoginAt).isEqualTo(12341235L)
    }

    @Test
    fun `유효 인증 코드로 계정을 활성화할 수 있다`() {
        //given
        var user = User(
            id = 1L,
            email = "injin.dev@gmail.com",
            nickname = "injin",
            status = UserStatus.PENDING,
            lastLoginAt = 120L,
            address = "Seoul",
            certificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"
        )

        //when
        user = user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")

        //then
        assertThat(user.status).isEqualTo(UserStatus.ACTIVE)
    }

    @Test
    fun `잘못된 인증 코드로 계정을 활성화 하려하면 에러를 던진다`() {
        //given
        var user = User(
            id = 1L,
            email = "injin.dev@gmail.com",
            nickname = "injin",
            status = UserStatus.PENDING,
            lastLoginAt = 120L,
            address = "Seoul",
            certificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"
        )

        //when
        //then
        assertThatThrownBy { user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac") }
            .isInstanceOf(CertificationCodeNotMatchedException::class.java)
    }
}
