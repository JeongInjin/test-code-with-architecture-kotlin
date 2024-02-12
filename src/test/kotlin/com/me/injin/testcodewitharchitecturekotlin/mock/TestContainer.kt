package com.me.injin.testcodewitharchitecturekotlin.mock

import com.me.injin.testcodewitharchitecturekotlin.common.service.port.ClockHolder
import com.me.injin.testcodewitharchitecturekotlin.common.service.port.UuidHolder
import com.me.injin.testcodewitharchitecturekotlin.post.controller.PostController
import com.me.injin.testcodewitharchitecturekotlin.post.controller.PostCreateController
import com.me.injin.testcodewitharchitecturekotlin.post.service.PostServiceImpl
import com.me.injin.testcodewitharchitecturekotlin.post.service.port.PostRepository
import com.me.injin.testcodewitharchitecturekotlin.user.controller.UserController
import com.me.injin.testcodewitharchitecturekotlin.user.controller.UserCreateController
import com.me.injin.testcodewitharchitecturekotlin.user.controller.port.UserService
import com.me.injin.testcodewitharchitecturekotlin.user.service.CertificationService
import com.me.injin.testcodewitharchitecturekotlin.user.service.UserServiceImpl
import com.me.injin.testcodewitharchitecturekotlin.user.service.port.MailSender
import com.me.injin.testcodewitharchitecturekotlin.user.service.port.UserRepository

class TestContainer(
    clockHolder: ClockHolder? = TestClockHolder(0L),
    uuidHolder: UuidHolder? = TestUuidHolder("test-uuid"),
) {

    val mailSender: MailSender = FakeMailSender()
    val userRepository: UserRepository = FakeUserRepository()
    val postRepository: PostRepository = FakePostRepository()
    val postService: PostServiceImpl = PostServiceImpl(
        postRepository = postRepository,
        userRepository = userRepository,
        clockHolder = clockHolder!!
    )
    val certificationService: CertificationService = CertificationService(mailSender)
    val userService: UserService = UserServiceImpl(
        userRepository = userRepository,
        clockHolder = clockHolder!!,
        certificationService = certificationService,
        uuidHolder = uuidHolder!!
    )
    val userController: UserController = UserController(userService = userService)
    val userCreateController: UserCreateController = UserCreateController(userService = userService)
    val postController: PostController = PostController(postService = postService)
    val postCreateController: PostCreateController = PostCreateController(postService = postService)
}
