package com.me.injin.testcodewitharchitecturekotlin.post.controller.response

import com.me.injin.testcodewitharchitecturekotlin.post.domain.Post
import com.me.injin.testcodewitharchitecturekotlin.user.domain.User
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PostResponseTest {

    @Test
    fun `Post 로 응답을 생성할 수 있다`() {
        //given
        val writer = User(
            id = 1L,
            email = "injin.dev@gmail.com",
            nickname = "injin",
            status = UserStatus.PENDING,
            lastLoginAt = 0L,
            address = "Seoul",
            certificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"
        )
        val post = Post(
            content = "content",
            writer = writer,
        )

        //when
        val postResponse = PostResponse.from(post)

        //then
        // then
        assertThat(postResponse.content).isEqualTo("content")
        assertThat(postResponse.writer!!.email).isEqualTo("injin.dev@gmail.com")
        assertThat(postResponse.writer!!.nickname).isEqualTo("injin")
        assertThat(postResponse.writer!!.status).isEqualTo(UserStatus.PENDING)
    }
}
