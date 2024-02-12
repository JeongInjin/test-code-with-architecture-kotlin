package com.me.injin.testcodewitharchitecturekotlin.user.service

import com.me.injin.testcodewitharchitecturekotlin.common.domain.exception.CertificationCodeNotMatchedException
import com.me.injin.testcodewitharchitecturekotlin.common.domain.exception.ResourceNotFoundException
import com.me.injin.testcodewitharchitecturekotlin.mock.FakeMailSender
import com.me.injin.testcodewitharchitecturekotlin.mock.FakeUserRepository
import com.me.injin.testcodewitharchitecturekotlin.mock.TestClockHolder
import com.me.injin.testcodewitharchitecturekotlin.mock.TestUuidHolder
import com.me.injin.testcodewitharchitecturekotlin.user.domain.User
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserCreate
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserUpdate
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class UserServiceImplTest {

    private lateinit var userService: UserServiceImpl

    @BeforeEach
    fun setup() {
        val userRepository = FakeUserRepository()
        userService = UserServiceImpl(
            userRepository = userRepository,
            certificationService = CertificationService(FakeMailSender()),
            uuidHolder = TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
            clockHolder = TestClockHolder(123456789L)
        )

        userRepository.save(
            User(
                id = 1L,
                email = "injin.dev@gmail.com",
                nickname = "injin",
                status = UserStatus.ACTIVE,
                lastLoginAt = 5555L,
                address = "Seoul",
                certificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"
            )
        )

        userRepository.save(
            User(
                id = 2L,
                email = "injin.prd@gmail.com",
                nickname = "injin-p",
                status = UserStatus.PENDING,
                lastLoginAt = 0L,
                address = "Pohang",
                certificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"
            )
        )

    }

    @Test
    fun `getByEmail 은 ACTIVE 상태인 유저를 찾아올 수 있다`() {
        //given
        val email = "injin.dev@gmail.com"

        //when
        val result = userService.getByEmail(email)

        //then
        assertThat(result.nickname).isEqualTo("injin")
    }

    @Test
    fun `getByEmail 은 PENDING 상태인 유저를 찾아올 수 없다`() {
        //given
        val email = "injin.dev2@gmail.com"

        // when
        // then
        assertThatThrownBy {
            userService.getByEmail(email)
        }.isInstanceOf(ResourceNotFoundException::class.java)
    }

    @Test
    fun `UserCreateDto 를 이용하여 유저를 생성할 수 있다`() {
        //given
        val userCreate = UserCreate(
            email = "injin.dev.test@gmail.com",
            nickname = "injin-t",
            address = "Gyeongi-t",
        )

        //when
        val result = userService.create(userCreate)

        //then
        assertThat(result.id).isNotNull()
        assertThat(result.status).isEqualTo(UserStatus.PENDING)
        assertThat(result.certificationCode).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
    }

    @Test
    fun `getById 은 ACTIVE 상태인 유저를 찾아올 수 있다`() {
        //given
        //when
        val result = userService.getById(1L)

        //then
        assertThat(result.nickname).isEqualTo("injin")
    }

    @Test
    fun `getById 은 PENDING 상태인 유저를 찾아올 수 없다`() {
        //given
        // when

        // then
        assertThatThrownBy {
            val result: User = userService.getById(2)
        }.isInstanceOf(ResourceNotFoundException::class.java)
    }

    @Test
    fun `UserCreateDto 를 이용하여 유저를 수정할 수 있다`() {
        //given
        val userCreate = UserUpdate(
            nickname = "injin-t",
            address = "Gyeongi-t",
        )

        //when
        val result = userService.update(1L, userCreate)

        //then
        // then
        val userEntity = userService.getById(1)
        assertThat(userEntity.id).isNotNull()
        assertThat(userEntity.address).isEqualTo("Gyeongi-t")
        assertThat(userEntity.nickname).isEqualTo("injin-t")
    }

    @Test
    fun `user 를 로그인 시키면 마지막 로그인 시간이 변경된다`() {
        //given
        //when
        userService.login(1L)

        //then
        val userEntity = userService.getById(1L)
        assertThat(userEntity.lastLoginAt).isEqualTo(123456789L)
    }

    @Test
    fun `PENDING 상태의 사용자는 인증 코드로 ACTIVE 시킬 수 있다`() {
        //given
        //when
        userService.verifyEmail(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")

        //then
        val userEntity = userService.getById(2)
        assertThat(userEntity.status).isEqualTo(UserStatus.ACTIVE)
    }

    @Test
    fun `PENDING 상태의 사용자는 잘못된 인증 코드를 받으면 에러를 던진다`() {
        // given
        // when
        // then
        AssertionsForClassTypes.assertThatThrownBy {
            userService.verifyEmail(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac")
        }.isInstanceOf(CertificationCodeNotMatchedException::class.java)
    }

}

