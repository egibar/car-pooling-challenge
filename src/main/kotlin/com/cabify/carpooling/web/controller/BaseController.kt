package com.cabify.carpooling.web.controller

import org.springframework.dao.DataIntegrityViolationException

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus


open class BaseController {

    // Convert a predefined exception to an HTTP Status code
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation") // 409
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun conflict() {}
}