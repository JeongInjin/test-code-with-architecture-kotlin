package com.me.injin.testcodewitharchitecturekotlin.mock

import com.me.injin.testcodewitharchitecturekotlin.common.service.port.ClockHolder

class TestClockHolder(
    private var millis: Long,
) : ClockHolder {

    override fun millis(): Long {
        return millis
    }
}
