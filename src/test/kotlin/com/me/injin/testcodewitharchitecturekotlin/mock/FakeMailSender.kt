package com.me.injin.testcodewitharchitecturekotlin.mock

import com.me.injin.testcodewitharchitecturekotlin.user.service.port.MailSender

class FakeMailSender() : MailSender {
    var email: String? = null
    var title: String? = null
    var content: String? = null
    override fun send(email: String, title: String, content: String) {
        this.email = email
        this.title = title
        this.content = content
    }
}
