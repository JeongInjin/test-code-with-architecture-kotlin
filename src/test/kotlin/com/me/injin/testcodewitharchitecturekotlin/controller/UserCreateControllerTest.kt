package com.me.injin.testcodewitharchitecturekotlin.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.me.injin.testcodewitharchitecturekotlin.model.dto.UserCreateDto
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup(
    Sql(value = ["/sql/delete-all-data.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
)
class UserCreateControllerTest(
    @Autowired private val mockMvc: MockMvc,
) {
    @MockBean
    private lateinit var mailSender: JavaMailSender

    private val objectMapper = ObjectMapper()

    @Test
    @Throws(Exception::class)
    fun `사용자는 회원 가입을 할 수있고 회원가입된 사용자는 PENDING 상태이다`() {
        // given
        val userCreateDto = UserCreateDto(
            email = "injin.dev@gmail.com",
            nickname = "injin",
            address = "Pangyo"
        )
        BDDMockito.doNothing().`when`(mailSender).send(ArgumentMatchers.any(SimpleMailMessage::class.java))

        // when
        // then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateDto))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("injin.dev@gmail.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("injin"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("PENDING"))
    }
}
