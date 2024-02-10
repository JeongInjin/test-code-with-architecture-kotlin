package com.me.injin.testcodewitharchitecturekotlin.model.dto

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Builder

data class UserCreateDto @Builder constructor(
    @param:JsonProperty("email")
    val email: String,

    @param:JsonProperty("nickname")
    val nickname: String,

    @param:JsonProperty("address")
    val address: String,
)
