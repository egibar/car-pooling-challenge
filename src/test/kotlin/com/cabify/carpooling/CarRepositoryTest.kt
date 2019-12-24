package com.cabify.carpooling

import com.cabify.carpooling.data.entities.CarEntity
import com.cabify.carpooling.data.repository.CarRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.util.AssertionErrors.assertEquals

@DataJpaTest
@ExtendWith(SpringExtension::class)
class CarRepositoryTest {

    @Autowired private lateinit var carRepository: CarRepository

    @Test
    fun `car repository saveAndFindCarById()`() {
        carRepository.save(CarEntity(1,6))
        val id = carRepository.findById(1).get().id
        assertEquals("asserting right id", id, 1L)
    }

}