package com.cabify.carpooling.service

import com.cabify.carpooling.data.entities.CarEntity
import com.cabify.carpooling.data.entities.toCar
import com.cabify.carpooling.data.repository.CarRepository
import com.cabify.carpooling.domain.Car
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CarService(val repo: CarRepository) {

    fun findById(id: Long): Car? {
        val carEntity: CarEntity? = repo.findByIdOrNull(id)
        carEntity?.let {
            return it.toCar()
        }
        return null
    }

    fun findAll(): List<Car> {
        return repo.findAll().map { it.toCar() }
    }
}