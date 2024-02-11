package com.me.injin.testcodewitharchitecturekotlin.user.domain

data class UserUpdate(
    var nickname: String,
    var address: String,
) {
    fun userUpdateDto() {
        this.nickname = nickname
        this.address = address
    }

}
