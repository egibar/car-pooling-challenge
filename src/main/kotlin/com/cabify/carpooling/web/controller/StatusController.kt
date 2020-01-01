package com.cabify.carpooling.web.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class StatusController {

    @GetMapping("/status")
    fun getStatus(): ResponseEntity<String> = ResponseEntity.ok().build()

}
