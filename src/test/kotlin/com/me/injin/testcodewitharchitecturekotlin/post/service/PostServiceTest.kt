package com.me.injin.testcodewitharchitecturekotlin.post.service

import com.me.injin.testcodewitharchitecturekotlin.post.domain.PostUpdate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup

@SpringBootTest
@SqlGroup(
    Sql(value = ["/sql/post-service-test-data.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    Sql(value = ["/sql/delete-all-data.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
)
class PostServiceTest(
    @Autowired private val postService: PostService,
) {

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
    fun `postUpdate 를 이용하여 게시물을 수정할 수 있다`() {
        //given
        val postUpdate = PostUpdate(
            content = "hello world"
        )
        //when
        val result = postService.update(1L, postUpdate)

        //then
        val postEntity = postService.getById(1L)
        assertThat(postEntity.content).isEqualTo("hello world")
        assertThat(postEntity.modifiedAt).isGreaterThan(0)
    }

}

