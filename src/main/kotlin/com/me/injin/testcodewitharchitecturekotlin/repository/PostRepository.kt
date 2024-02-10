package com.me.injin.testcodewitharchitecturekotlin.repository

import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<PostEntity, Long>
