package com.me.injin.testcodewitharchitecturekotlin.post.controller

import com.me.injin.testcodewitharchitecturekotlin.common.domain.exception.ResourceNotFoundException
import com.me.injin.testcodewitharchitecturekotlin.mock.TestClockHolder
import com.me.injin.testcodewitharchitecturekotlin.mock.TestContainer
import com.me.injin.testcodewitharchitecturekotlin.post.domain.Post
import com.me.injin.testcodewitharchitecturekotlin.post.domain.PostUpdate
import com.me.injin.testcodewitharchitecturekotlin.user.domain.User
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatusCode

class PostControllerTest {

    @Test
    fun `사용자는 게시물을 단건 조회할 수 있다`() {
        //given
        val testContainer = TestContainer()
        val user = User(
            id = 1L,
            email = "injin.dev@gmail.com",
            nickname = "injin",
            status = UserStatus.ACTIVE,
            lastLoginAt = 123123L,
            address = "Seoul",
            certificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"
        )
        testContainer.userRepository.save(user)
        testContainer.postRepository.save(
            Post(
                id = 1L,
                writer = user,
                content = "helloworld",
                createdAt = 123L
            )
        )

        //when
        val postById = testContainer.postController.getById(1L)

        //then
        assertThat(postById.statusCode).isEqualTo(HttpStatusCode.valueOf(200))
        assertThat(postById.body).isNotNull()
        assertThat(postById.body?.content).isEqualTo("helloworld")
        assertThat(postById.body?.writer?.nickname).isEqualTo("injin")
        assertThat(postById.body?.createdAt).isEqualTo(123L)
    }

    @Test
    @Throws(Exception::class)
    fun `사용자가 존재하지 않는 게시물을 조회할 경우 에러가 난다`() {
        // given
        val testContainer = TestContainer()

        // when
        // then
        assertThatThrownBy { testContainer.postController.getById(1L) }
            .isInstanceOf(ResourceNotFoundException::class.java)

    }

    @Test
    @Throws(Exception::class)
    fun 사용자는_게시물을_수정할_수_있다() {
        // given
        val testContainer = TestContainer(clockHolder = TestClockHolder(1313133))
        val user = User(
            id = 1L,
            email = "injin.dev@gmail.com",
            nickname = "injin",
            status = UserStatus.ACTIVE,
            lastLoginAt = 123123L,
            address = "Seoul",
            certificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"
        )
        testContainer.userRepository.save(user)
        testContainer.postRepository.save(
            Post(
                id = 1L,
                writer = user,
                content = "helloworld",
                createdAt = 123L
            )
        )

        // when
        val result = testContainer.postController.update(1L, PostUpdate(content = "foobar"))

        // then
        assertThat(result.statusCode).isEqualTo(HttpStatusCode.valueOf(200))
        assertThat(result.body).isNotNull()
        assertThat(result.body?.content).isEqualTo("foobar")
        assertThat(result.body?.writer?.nickname).isEqualTo("injin")
        assertThat(result.body?.createdAt).isEqualTo(123L)
        assertThat(result.body?.modifiedAt).isEqualTo(1313133L)

    }
}
