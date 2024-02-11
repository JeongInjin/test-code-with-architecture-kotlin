package com.me.injin.testcodewitharchitecturekotlin.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.me.injin.testcodewitharchitecturekotlin.model.dto.PostCreateDto
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup(
    Sql(value = ["/sql/post-create-controller-test-data.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    Sql(value = ["/sql/delete-all-data.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
)
class PostCreateControllerTest(
    @Autowired private val mockMvc: MockMvc,
) {
    private val objectMapper = ObjectMapper()

    @Test
    fun `사용자는 게시물을 작성할 수 있다`() {
        // given
        val postCreate = PostCreateDto(
            writerId = 1,
            content = "helloworld"
        )

        // when
        // then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postCreate))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("helloworld"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.writer.id").isNumber())
            .andExpect(MockMvcResultMatchers.jsonPath("$.writer.email").value("injin.dev@gmail.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.writer.nickname").value("injin"))
    }
}

