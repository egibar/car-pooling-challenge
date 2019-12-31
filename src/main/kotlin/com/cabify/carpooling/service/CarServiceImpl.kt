package com.cabify.carpooling.service

import com.cabify.carpooling.data.entities.toCar
import com.cabify.carpooling.data.entities.toCarEntity
import com.cabify.carpooling.data.repository.CarRepository
import com.cabify.carpooling.domain.Car
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

interface CarService {
    fun findById(id: Long): Car?
    fun findAll(): List<Car>
    fun save(car: Car)
    fun saveAll(cars: List<Car>)
}

@Service
class CarServiceImpl(val carRepository: CarRepository) : CarService {

    override fun findById(id: Long): Car? {
        return carRepository.findByIdOrNull(id)?.let { it.toCar() }
    }

    override fun findAll(): List<Car> {
        return carRepository.findAll().map { it.toCar() }
    }

    override fun save(car: Car) {
        val carEntity = car.toCarEntity()
        carRepository.save(carEntity)
    }

    override fun saveAll(cars: List<Car>) {
        val carEntities = cars.map { it.toCarEntity() }
        carRepository.saveAll(carEntities)
    }

}