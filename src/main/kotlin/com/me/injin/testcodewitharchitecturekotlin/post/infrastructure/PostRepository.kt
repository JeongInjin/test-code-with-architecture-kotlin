package com.me.injin.testcodewitharchitecturekotlin.post.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<PostEntity, Long>
