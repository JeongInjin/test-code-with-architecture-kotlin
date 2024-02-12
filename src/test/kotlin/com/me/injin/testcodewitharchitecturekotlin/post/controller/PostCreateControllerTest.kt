package com.me.injin.testcodewitharchitecturekotlin.post.controller

import com.me.injin.testcodewitharchitecturekotlin.mock.TestClockHolder
import com.me.injin.testcodewitharchitecturekotlin.mock.TestContainer
import com.me.injin.testcodewitharchitecturekotlin.post.domain.PostCreate
import com.me.injin.testcodewitharchitecturekotlin.user.domain.User
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatusCode

class PostCreateControllerTest {
    @Test
    fun `사용자는 게시물을 작성할 수 있다`() {

        // given
        val testContainer = TestContainer(clockHolder = TestClockHolder(1313133))
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

        val postCreate = PostCreate(
            writerId = 1,
            content = "helloworld"
        )

        // when
        val result = testContainer.postCreateController.create(postCreate)

        // then
        assertThat(result.statusCode).isEqualTo(HttpStatusCode.valueOf(201))
        assertThat(result.body).isNotNull()
        assertThat(result.body?.content).isEqualTo("helloworld")
        assertThat(result.body?.writer?.nickname).isEqualTo("injin")
        assertThat(result.body?.createdAt).isEqualTo(1313133L)
    }
}

