package com.me.injin.testcodewitharchitecturekotlin.post.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.me.injin.testcodewitharchitecturekotlin.post.domain.PostUpdate
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup(
    Sql(value = ["/sql/post-service-test-data.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    Sql(value = ["/sql/delete-all-data.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
)
class PostControllerTest(
    @Autowired private val mockMvc: MockMvc,
) {

    private val objectMapper = ObjectMapper()

    @Test
    fun `사용자는 게시물을 단건 조회할 수 있다`() {
        //given
        //when
        //then
        mockMvc.perform(get("/api/posts/1"))
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.content").value("helloworld"))
            .andExpect(jsonPath("$.writer.id").isNumber())
            .andExpect(jsonPath("$.writer.email").value("injin.dev@gmail.com"))
            .andExpect(jsonPath("$.writer.nickname").value("injin"));
    }

    @Test
    @Throws(Exception::class)
    fun `사용자가 존재하지 않는 게시물을 조회할 경우 에러가 난다`() {
        // given
        // when
        // then
        mockMvc.perform(get("/api/posts/123456789"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Posts에서 ID 123456789를 찾을 수 없습니다."))
    }

    @Test
    @Throws(Exception::class)
    fun 사용자는_게시물을_수정할_수_있다() {
        // given
        val postUpdate = PostUpdate(
            content = "foobar"
        )
        // when
        // then
        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postUpdate))
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.content").value("foobar"))
            .andExpect(jsonPath("$.writer.id").isNumber())
            .andExpect(jsonPath("$.writer.email").value("injin.dev@gmail.com"))
            .andExpect(jsonPath("$.writer.nickname").value("injin"))
    }
}
