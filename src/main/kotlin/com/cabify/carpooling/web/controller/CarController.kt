package com.cabify.carpooling.web.controller

import com.cabify.carpooling.data.entities.CarEntity
import com.cabify.carpooling.data.entities.toCar
import com.cabify.carpooling.data.repository.CarRepository
import com.cabify.carpooling.domain.Car
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/car"])
class CarController (val repo: CarRepository) {

    @GetMapping("/{id}")
    fun getCarById(@PathVariable id: Long): ResponseEntity<Car> {
        val carEntity: CarEntity? = repo.findByIdOrNull(id)
        carEntity?.let {
            return ResponseEntity(it.toCar(), HttpStatus.OK)
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findall")
    fun all(): List<Car> {
        return repo.findAll().map { it.toCar() }
    }

}
