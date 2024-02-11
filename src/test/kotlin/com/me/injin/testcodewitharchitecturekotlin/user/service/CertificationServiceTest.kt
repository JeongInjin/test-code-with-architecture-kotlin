package com.me.injin.testcodewitharchitecturekotlin.user.service

import com.me.injin.testcodewitharchitecturekotlin.mock.FakeMailSender
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CertificationServiceTest {

    @Test
    fun `이메일과 컨텐츠가 제대로 만들어져서 보내지는지 테스트한다`() {
        //given
        val fakeMailSender = FakeMailSender()
        val certificationService = CertificationService(fakeMailSender)

        //when
        certificationService.send("injin.dev@gmail.com", 1, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")

        //then
        //then
        assertThat(fakeMailSender.email).isEqualTo("injin.dev@gmail.com")
        assertThat(fakeMailSender.title).isEqualTo("Please certify your email address")
        assertThat(fakeMailSender.content).isEqualTo("Please click the following link to certify your email address: http://localhost:8080/api/users/1/verify?certificationCode=aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
    }
}
