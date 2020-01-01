package com.cabify.carpooling.web.controller

import com.cabify.carpooling.domain.Car
import com.cabify.carpooling.domain.Journey
import com.cabify.carpooling.service.CarService
import com.cabify.carpooling.service.JourneyService
import com.cabify.carpooling.service.ResetService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotEmpty

@Validated
@RestController
@RequestMapping(value = ["/cars"])
class CarController(val carService: CarService, val journeyService: JourneyService, val resetService: ResetService) : BaseController() {

    @GetMapping("/{id}")
    fun getCarById(@PathVariable id: Long): ResponseEntity<Car> {
        val car: Car? = carService.findById(id)
        car?.let {
            return ResponseEntity(it, HttpStatus.OK)
        }
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/findall")
    fun all(): List<Car> {
        return carService.findAll()
    }

    @PutMapping("")
    fun save(@RequestBody
             @NotEmpty(message = "Input car list cannot be empty.")
             @Valid
             cars: List<Car>): ResponseEntity<Void> {
        // clean db first
        resetService.resetDb()

        carService.saveAll(cars)
        return ResponseEntity.ok().build()
    }

    /**
     * Empties the db
     */
    @GetMapping("/reset")
    fun reset(): ResponseEntity<Void> {
        resetService.resetDb()
        return ResponseEntity.ok().build()
    }

    /**
     * Initializes the db with some cars and journeys for testing purposes
     */
    @GetMapping("/init")
    fun initializeDB(): ResponseEntity<Void> {
        val carList: List<Car> = listOf(Car(1, 4), Car(2, 5), Car(3, 6))
        carService.saveAll(carList)
        journeyService.save(Journey(1, 3))
        journeyService.save(Journey(2, 2))
        journeyService.save(Journey(3, 2))
        return ResponseEntity.ok().build()
    }


}
