package com.me.injin.testcodewitharchitecturekotlin.mock

import com.me.injin.testcodewitharchitecturekotlin.user.domain.User
import com.me.injin.testcodewitharchitecturekotlin.user.domain.UserStatus
import com.me.injin.testcodewitharchitecturekotlin.user.service.port.UserRepository

class FakeUserRepository() : UserRepository {

    private val autoGeneratedId = 0L
    private val data = mutableListOf<User>()

    override fun findById(id: Long): User? {
        return data.find { it.id == id }
    }

    override fun findByIdAndStatus(id: Long, userStatus: UserStatus): User? {
        return data.find { it.id == id && it.status == userStatus }
    }

    override fun findByEmailAndStatus(email: String?, userStatus: UserStatus): User? {
        return data.find { it.email == email && it.status == userStatus }
    }

    override fun save(user: User): User {
        if (user.id == null || user.id == 0L) {
            val newUser = user.copy(id = autoGeneratedId + 1)
            data.add(newUser)
            return newUser
        } else {
            data.removeIf { it.id == user.id }
            data.add(user)
            return user
        }
    }
}
