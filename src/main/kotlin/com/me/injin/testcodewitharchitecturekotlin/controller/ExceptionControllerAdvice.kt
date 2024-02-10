package com.me.injin.testcodewitharchitecturekotlin.controller

import com.me.injin.testcodewitharchitecturekotlin.exception.CertificationCodeNotMatchedException
import com.me.injin.testcodewitharchitecturekotlin.exception.ResourceNotFoundException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class ExceptionControllerAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(
        ResourceNotFoundException::class
    )
    fun resourceNotFoundException(exception: ResourceNotFoundException): String {
        return exception.message.toString()
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(
        CertificationCodeNotMatchedException::class
    )
    fun certificationCodeNotMatchedException(exception: CertificationCodeNotMatchedException): String {
        return exception.message.toString()
    }
}
