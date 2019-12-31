package com.cabify.carpooling.data

import com.cabify.carpooling.data.entities.CarEntity
import com.cabify.carpooling.data.entities.JourneyEntity
import com.cabify.carpooling.data.repository.CarRepository
import com.cabify.carpooling.data.repository.JourneyRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.util.AssertionErrors.assertEquals

@DataJpaTest
@ExtendWith(SpringExtension::class)
class CarRepositoryTest {

    @Autowired
    private lateinit var carRepository: CarRepository
    @Autowired
    private lateinit var journeyRepository: JourneyRepository

    @Test
    fun `car repository saveAndFindCarById()`() {
        carRepository.save(CarEntity(1, 6))
        val id = carRepository.findById(1).get().id
        assertEquals("asserting right id", 1L, id)
    }

    @Test
    fun `car repository findAvailable should find car 2`() {
        carRepository.save(CarEntity(1, 4))
        val car2 = CarEntity(2, 5)
        carRepository.save(car2)
        journeyRepository.save(JourneyEntity(1, 2, car = car2))
        journeyRepository.save(JourneyEntity(2, 2, car = car2))
        val car = carRepository.findFirstAvailable(1)
        assertEquals("available cars found", 1L, car?.id)
    }

    @Test
    fun `car repository findAvailable() should find car 1`() {
        val car1 = CarEntity(1, 4)
        val car2 = CarEntity(2, 5)
        carRepository.save(car1)
        carRepository.save(car2)
        journeyRepository.save(JourneyEntity(1, 1, car = car1))
        journeyRepository.save(JourneyEntity(2, 2, car = car1))
        val car = carRepository.findFirstAvailable(1)
        assertEquals("available cars found", 1L, car?.id)
    }

}