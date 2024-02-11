package com.me.injin.testcodewitharchitecturekotlin.service

import com.me.injin.testcodewitharchitecturekotlin.model.UserStatus.PENDING
import com.me.injin.testcodewitharchitecturekotlin.model.dto.UserCreateDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender

@SpringBootTest
class UserServiceTest2(
    @Autowired private val userService: UserService,
) {

    @MockBean
    private lateinit var mailSender: JavaMailSender

    @Test
    fun `UserCreateDto 를 이용하여 유저를 생성할 수 있다`() {
        //given
        val userCreateDto = UserCreateDto(
            email = "injin.dev.test@gmail.com",
            nickname = "injin-t",
            address = "Gyeongi-t",
        )
        BDDMockito.doNothing().`when`(mailSender).send(any(SimpleMailMessage::class.java))

        //when
        val result = userService.create(userCreateDto)

        //then
        assertThat(result.id).isNotNull()
        assertThat(result.status).isEqualTo(PENDING)
//        assertThat(result.certificationCode).isEqualTo("????")
    }

}

