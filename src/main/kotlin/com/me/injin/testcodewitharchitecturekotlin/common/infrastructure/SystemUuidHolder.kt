package com.me.injin.testcodewitharchitecturekotlin.common.infrastructure

import com.me.injin.testcodewitharchitecturekotlin.common.service.port.UuidHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class SystemUuidHolder : UuidHolder {
    override fun random(): String {
        return UUID.randomUUID().toString()
    }
}
