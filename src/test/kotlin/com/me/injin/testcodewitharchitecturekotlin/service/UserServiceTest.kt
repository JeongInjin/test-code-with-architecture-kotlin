package com.me.injin.testcodewitharchitecturekotlin.service

import com.me.injin.testcodewitharchitecturekotlin.exception.ResourceNotFoundException
import com.me.injin.testcodewitharchitecturekotlin.repository.UserEntity
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.Sql.ExecutionPhase
import org.springframework.test.context.jdbc.SqlGroup

@SpringBootTest
@SqlGroup(
    Sql(value = ["/sql/user-service-test-data.sql"], executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
    Sql(value = ["/sql/delete-all-data.sql"], executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
)
class UserServiceTest(
    @Autowired private val userService: UserService,
    @Autowired private val mailSender: JavaMailSender,
) {
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
    fun `getByEmail 은 PENDING 상태인 유저를 찾아올 수 있다`() {
        //given
        val email = "injin.dev2@gmail.com"

        // when
        // then
        assertThatThrownBy {
            val result: UserEntity = userService.getByEmail(email)
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
    fun `getById 은 PENDING 상태인 유저를 찾아올 수 있다`() {
        //given
        // when

        // then
        assertThatThrownBy {
            val result: UserEntity = userService.getById(2)
        }.isInstanceOf(ResourceNotFoundException::class.java)
    }

}

