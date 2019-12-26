package com.cabify.carpooling.web.controller

import com.cabify.carpooling.domain.Car
import com.cabify.carpooling.service.CarService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/car"])
class CarController(val carService: CarService) {

    @GetMapping("/{id}")
    fun getCarById(@PathVariable id: Long): ResponseEntity<Car> {
        val car: Car? = carService.findById(id)
        car?.let {
            return ResponseEntity(it, HttpStatus.OK)
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findall")
    fun all(): List<Car> {
        return carService.findAll()
    }

}
