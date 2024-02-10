package com.me.injin.testcodewitharchitecturekotlin.model.dto

data class PostUpdateDto(
    var content: String? = null,
) {
    fun postUpdateDto() {
        this.content = content
    }

}
