package com.cabify.carpooling.web.controller

import com.cabify.carpooling.domain.Car
import com.cabify.carpooling.service.CarService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/cars"])
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

    @PutMapping("")
    fun save(@Valid @RequestBody cars: List<Car>): ResponseEntity<Void> {
        carService.saveAll(cars)
        return ResponseEntity.ok().build();
    }

    @GetMapping("/init")
    fun initializeDB(): ResponseEntity<Void> {
        val carList: List<Car> = listOf(Car(1, 4), Car(2, 5), Car(3, 6))
        carService.saveAll(carList)
        return ResponseEntity.ok().build();
    }


}
