package com.cabify.carpooling.web.controller

import com.cabify.carpooling.data.repository.CarRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/"])
class StatusController(val repo: CarRepository) {

    @GetMapping("/status")
    fun getStatus(): ResponseEntity<String> = ResponseEntity.ok().build()

}
