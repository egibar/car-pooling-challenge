package com.cabify.carpooling.data.repository

import com.cabify.carpooling.data.entities.CarEntity
import org.springframework.data.repository.CrudRepository

interface CarRepository : CrudRepository<CarEntity, Long> {

//    @Query("SELECT * FROM car_entity")
//    fun findByIdOrNull(name: String?): Car
//    fun findByEmail(email: String?): Employee?
//    @Query("SELECT * FROM car_entity")
//    override fun findAll(): Flux<CarEntity>
}