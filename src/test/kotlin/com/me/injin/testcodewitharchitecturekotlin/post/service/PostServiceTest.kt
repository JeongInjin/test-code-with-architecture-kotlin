package com.me.injin.testcodewitharchitecturekotlin.post.service

import com.me.injin.testcodewitharchitecturekotlin.mock.FakePostRepository
import com.me.injin.testcodewitharchitecturekotlin.mock.FakeUserRepository
import com.me.injin.testcodewitharchitecturekotlin.mock.TestClockHolder
import com.me.injin.testcodewitharchitecturekotlin.post.domain.Post
import com.me.injin.testcodewitharchitecturekotlin.post.domain.PostCreate
import com.me.injin.testcodewitharchitecturekotlin.post.domain.PostUpdate
import com.me.injin.testcodewitharchitecturekotlin.user.domain.User
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class PostServiceTest {

    private lateinit var postService: PostService

    @BeforeEach
    fun setup() {
        val postRepository = FakePostRepository()
        val userRepository = FakeUserRepository()
        postService = PostService(
            postRepository = postRepository,
            userRepository = userRepository,
            clockHolder = TestClockHolder(123456789L)
        )

        val user1 = User(
            id = 1L,
            email = "injin.dev@gmail.com",
            nickname = "injin",
            status = UserStatus.ACTIVE,
            lastLoginAt = 5555L,
            address = "Seoul",
            certificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"
        )

        val user2 = User(
            id = 2L,
            email = "injin.prd@gmail.com",
            nickname = "injin-p",
            status = UserStatus.PENDING,
            lastLoginAt = 0L,
            address = "Pohang",
            certificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"
        )

        userRepository.save(user1)
        userRepository.save(user2)

        postRepository.save(
            Post(
                id = 1L,
                writer = user1,
                content = "helloworld",
                createdAt = 123456789L,
                modifiedAt = 123456789L
            )
        )
    }

    @Test
    fun `getById 는 존재하는 게시물을 내려준다`() {
        //given
        //when
        val result = postService.getById(1L)

        //then
        assertThat(result.content).isEqualTo("helloworld")
        assertThat(result.writer.email).isEqualTo("injin.dev@gmail.com")
    }

    @Test
    fun `postCreate 를 이용하여 게시물을 생성할 수 있다`() {
        //given
        val postCreate = PostCreate(
            writerId = 1L,
            content = "hello"
        )
        //when
        val result = postService.create(postCreate)

        //then
        // then
        assertThat(result.id).isNotNull()
        assertThat(result.content).isEqualTo("hello")
        assertThat(result.createdAt).isEqualTo(123456789L)
    }

    @Test
    fun `postUpdate 를 이용하여 게시물을 수정할 수 있다`() {
        //given
        val postUpdate = PostUpdate(
            content = "hello world"
        )
        //when
        val result = postService.update(1L, postUpdate)

        //then
        val post = postService.getById(1L)
        assertThat(post.content).isEqualTo("hello world")
        assertThat(post.modifiedAt).isEqualTo(123456789L)
    }

}

