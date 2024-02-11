package com.me.injin.testcodewitharchitecturekotlin.repository

import com.me.injin.testcodewitharchitecturekotlin.model.UserStatus.*
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.jdbc.Sql
import java.util.*


@DataJpaTest(showSql = true)
@Sql("/sql/user-repository-test-data.sql")
class UserRepositoryTest(
    @Autowired private val userRepository: UserRepository,
) {

    @Test
    @Throws(Exception::class)
    fun `findByIdAndStatus 로 유저 데이터를 찾아올 수 있다`() {
        //given
        //when
        val result = userRepository.findByIdAndStatus(1L, ACTIVE)

        //then
        assertThat(result).isNotNull()
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun `findByIdAndStatus 데이터가 없으면 null 을 반환한다`() {
        //given
        //when
        val result = userRepository.findByIdAndStatus(1L, PENDING)

        //then
        assertThat(result).isNull()
    }

    @Test
    @Throws(Exception::class)
    fun `findByEmailAndStatus 로 유저 데이터를 찾아올 수 있다`() {
        //given
        //when
        val result = userRepository.findByEmailAndStatus("injin.dev@gmail.com", ACTIVE)

        //then
        assertThat(result).isNotNull()
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun `findByEmailAndStatus 데이터가 없으면 null 을 반환한다`() {
        //given
        //when
        val result = userRepository.findByEmailAndStatus("injin.dev@gmail.com", PENDING)

        //then
        assertThat(result).isNull()
    }

}
