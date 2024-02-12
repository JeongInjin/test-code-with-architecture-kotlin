package com.me.injin.testcodewitharchitecturekotlin.medium

import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserCreate
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus
import com.me.injin.testcodewitharchitecturekotlin.user.service.UserServiceImpl
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
class UserServiceImplTest2(
    @Autowired private val userService: UserServiceImpl,
) {

    @MockBean
    private lateinit var mailSender: JavaMailSender

    @Test
    fun `UserCreateDto 를 이용하여 유저를 생성할 수 있다`() {
        //given
        val userCreate = UserCreate(
            email = "injin.dev.test@gmail.com",
            nickname = "injin-t",
            address = "Gyeongi-t",
        )
        BDDMockito.doNothing().`when`(mailSender).send(any(SimpleMailMessage::class.java))

        //when
        val result = userService.create(userCreate)

        //then
        assertThat(result.id).isNotNull()
        assertThat(result.status).isEqualTo(UserStatus.PENDING)
//        assertThat(result.certificationCode).isEqualTo("????")
    }

}

