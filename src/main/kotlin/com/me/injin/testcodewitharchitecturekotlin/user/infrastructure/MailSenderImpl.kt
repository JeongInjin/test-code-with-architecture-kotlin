package com.me.injin.testcodewitharchitecturekotlin.user.infrastructure

import com.me.injin.testcodewitharchitecturekotlin.user.service.port.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class MailSenderImpl(
    private var javaMailSender: JavaMailSender,
) : MailSender {
    override fun send(email: String, title: String, content: String) {
        val message = SimpleMailMessage().apply {
            setTo(email)
            subject = title
            text = content
        }
        javaMailSender.send(message)
    }
}
