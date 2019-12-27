package com.cabify.carpooling.service

import com.cabify.carpooling.data.entities.CarEntity
import com.cabify.carpooling.data.entities.toCar
import com.cabify.carpooling.data.entities.toCarEntity
import com.cabify.carpooling.data.entities.toJourneyEntity
import com.cabify.carpooling.data.repository.CarRepository
import com.cabify.carpooling.domain.Car
import com.cabify.carpooling.domain.Journey
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CarService(val carRepository: CarRepository) {

    fun findById(id: Long): Car? {
        val carEntity: CarEntity? = carRepository.findByIdOrNull(id)
        carEntity?.let {
            return it.toCar()
        }
        return null
    }

    fun findAll(): List<Car> {
        return carRepository.findAll().map { it.toCar() }
    }

    fun save(car: Car) {
        val carEntity = car.toCarEntity()
        carRepository.save(carEntity)
    }

    fun saveAll(cars: List<Car>) {
        val carEntities = cars.map { it.toCarEntity() }
        carRepository.saveAll(carEntities)
    }
}