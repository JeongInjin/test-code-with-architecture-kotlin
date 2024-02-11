package com.me.injin.testcodewitharchitecturekotlin.post.service

import com.me.injin.testcodewitharchitecturekotlin.post.domain.PostCreate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup

@SpringBootTest
@SqlGroup(
    Sql(value = ["/sql/user-service-test-data.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    Sql(value = ["/sql/delete-all-data.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
)
class PostServiceTest2(
    @Autowired private val postService: PostService,
) {

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
        assertThat(result.createdAt).isGreaterThan(0)
    }

}

