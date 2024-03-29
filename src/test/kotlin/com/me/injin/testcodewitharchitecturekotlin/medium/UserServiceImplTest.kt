package com.me.injin.testcodewitharchitecturekotlin.medium

import com.me.injin.testcodewitharchitecturekotlin.common.domain.exception.CertificationCodeNotMatchedException
import com.me.injin.testcodewitharchitecturekotlin.common.domain.exception.ResourceNotFoundException
import com.me.injin.testcodewitharchitecturekotlin.user.domain.User
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserUpdate
import com.me.injin.testcodewitharchitecturekotlin.user.service.UserServiceImpl
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD
import org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD
import org.springframework.test.context.jdbc.SqlGroup

@SpringBootTest
@SqlGroup(
    Sql(value = ["/sql/user-service-test-data.sql"], executionPhase = BEFORE_TEST_METHOD),
    Sql(value = ["/sql/delete-all-data.sql"], executionPhase = AFTER_TEST_METHOD)
)
class UserServiceImplTest(
    @Autowired private val userService: UserServiceImpl,
) {

    @MockBean
    private lateinit var mailSender: JavaMailSender


    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
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
        assertThat(userEntity.lastLoginAt).isGreaterThan(0L)
//        assertThat(userEntity.lastLoginAt).isEqualTo("T.T") // FIXME
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

