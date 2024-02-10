package com.me.injin.testcodewitharchitecturekotlin.model.dto

data class UserUpdateDto(
    var nickname: String,
    var address: String,
) {
    fun userUpdateDto() {
        this.nickname = nickname
        this.address = address
    }

}
