package com.me.injin.testcodewitharchitecturekotlin.mock

import com.me.injin.testcodewitharchitecturekotlin.common.service.port.UuidHolder

class TestUuidHolder(
    private var uuid: String,
) : UuidHolder {
    override fun random(): String {
        return uuid
    }
}
