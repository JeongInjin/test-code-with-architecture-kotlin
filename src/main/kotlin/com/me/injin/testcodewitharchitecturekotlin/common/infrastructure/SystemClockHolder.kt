package com.me.injin.testcodewitharchitecturekotlin.common.infrastructure

import com.me.injin.testcodewitharchitecturekotlin.common.service.port.ClockHolder
import org.springframework.stereotype.Component
import java.time.Clock

@Component
class SystemClockHolder : ClockHolder {
    override fun millis(): Long {
        return Clock.systemUTC().millis()
    }
}
