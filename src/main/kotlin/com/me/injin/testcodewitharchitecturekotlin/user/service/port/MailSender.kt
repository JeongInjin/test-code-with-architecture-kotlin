package com.me.injin.testcodewitharchitecturekotlin.user.service.port

interface MailSender {
    fun send(email: String, title: String, content: String)
}
