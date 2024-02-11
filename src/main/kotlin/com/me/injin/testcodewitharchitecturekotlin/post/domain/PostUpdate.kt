package com.me.injin.testcodewitharchitecturekotlin.post.domain

data class PostUpdate(
    var content: String? = null,
) {
    fun postUpdateDto() {
        this.content = content
    }

}
