package com.cabify.carpooling.web.controller

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException


open class BaseController {

    // Convert a predefined exception to an HTTP Status code
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation") // 409
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun conflict() {}


    // Convert a predefined exception to an HTTP Status code
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad request") // 400
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun badRequestInvalidData(ex: MethodArgumentNotValidException): ResponseEntity<List<String>> {
        val errors = ex.bindingResult.allErrors
        val errorMessages = mutableListOf<String>()
        for (error in errors) {
            error.defaultMessage?.let { errorMessages.add(it) }
        }
        return ResponseEntity(errorMessages, HttpStatus.BAD_REQUEST)
    }


    /**
     * Handles ConstraintViolationException that happens when the Car controller gets a list of invalid cars
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad request") // 400
    @ExceptionHandler(ConstraintViolationException::class)
    fun badRequestInvalidDataList(exception: ConstraintViolationException): ResponseEntity<String> {
        val violations: Set<ConstraintViolation<*>> = exception.constraintViolations
        val builder = StringBuilder()
        for (violation in violations) {
            builder.append(violation.message)
            break
        }
        return ResponseEntity(builder.toString(), HttpStatus.BAD_REQUEST)
    }
}