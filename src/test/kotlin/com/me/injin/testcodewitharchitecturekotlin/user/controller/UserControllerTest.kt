package com.me.injin.testcodewitharchitecturekotlin.user.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserUpdate
import com.me.injin.testcodewitharchitecturekotlin.user.infrastructure.UserEntity
import com.me.injin.testcodewitharchitecturekotlin.user.infrastructure.UserRepository
import org.assertj.core.api.Assertions.assertThat
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
    Sql(value = ["/sql/user-controller-test-data.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    Sql(value = ["/sql/delete-all-data.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
)
class UserControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val userRepository: UserRepository,
) {

    private val objectMapper = ObjectMapper()

    @Test
    fun `사용자는 특정 유저의 정보를 개인정보는 소거된채 전달 받을 수 있다`() {
        // given
        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("injin.dev@gmail.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("injin"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.address").doesNotExist())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("ACTIVE"))
    }

    @Test
    fun `사용자는 존재하지 않는 유저의 아이디로 api 호출할 경우 404 응답을 받는다`() {
        // given
        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/123456789"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Users에서 ID 123456789를 찾을 수 없습니다."))
    }

    @Test
    fun `사용자는 인증 코드로 계정을 활성화 시킬 수 있다`() {
        // given
        // when
        // then
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/users/2/verify")
                .queryParam("certificationCode", "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
        )
            .andExpect(MockMvcResultMatchers.status().isFound())
        val userEntity: UserEntity = userRepository.findById(1L).get()
        assertThat(userEntity.status).isEqualTo(UserStatus.ACTIVE)
    }

    @Test
    fun `사용자는 인증 코드가 일치하지 않을 경우 권한 없음 에러를 내려준다`() {
        // given
        // when
        // then
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/users/2/verify")
                .queryParam("certificationCode", "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac")
        )
            .andExpect(MockMvcResultMatchers.status().isForbidden())
    }

    @Test
    fun `사용자는 내 정보를 불러올 때 개인정보인 주소도 갖고 올 수 있다`() {
        // given
        // when
        // then
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/users/me")
                .header("EMAIL", "injin.dev@gmail.com")
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("injin.dev@gmail.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("injin"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Seoul"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("ACTIVE"))
    }

    @Test
    fun `사용자는 내 정보를 수정할 수 있다`() {
        // given
        val userUpdate = UserUpdate(
            nickname = "injin-n",
            address = "Pangyo"
        )

        // when
        // then
        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/users/me")
                .header("EMAIL", "injin.dev@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userUpdate))
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("injin.dev@gmail.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("injin-n"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Pangyo"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("ACTIVE"))
    }

}
