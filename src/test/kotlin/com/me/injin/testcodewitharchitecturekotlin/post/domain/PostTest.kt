package com.me.injin.testcodewitharchitecturekotlin.post.domain

import com.me.injin.testcodewitharchitecturekotlin.user.domain.User
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PostTest {

    @Test
    fun `PostCreate 로 게시물을 만들 수 있다`() {
        //given
        val postCreate = PostCreate(1L, "content")
        val writer = User(
            id = 1L,
            email = "injin.dev@gmail.com",
            nickname = "injin",
            status = UserStatus.ACTIVE,
            lastLoginAt = 0L,
            address = "Seoul",
            certificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"
        )
        //when
        val post = Post.from(writer, postCreate)

        //then
        // then
        assertThat(post.content).isEqualTo("content")
        assertThat(post.writer.email).isEqualTo("injin.dev@gmail.com")
        assertThat(post.writer.nickname).isEqualTo("injin")
        assertThat(post.writer.address).isEqualTo("Seoul")
        assertThat(post.writer.status).isEqualTo(UserStatus.ACTIVE)
        assertThat(post.writer.certificationCode).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
    }
}
