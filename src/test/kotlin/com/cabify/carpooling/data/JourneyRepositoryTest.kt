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
class JourneyRepositoryTest {

    @Autowired
    private lateinit var journeyRepository: JourneyRepository

    @Autowired
    private lateinit var carRepository: CarRepository

    @Test
    fun `journey repository save and find`() {
        journeyRepository.save(JourneyEntity(1, 6))
        val id = journeyRepository.findById(1).get().id
        assertEquals("asserting right id", 1L, id)
    }

    @Test
    fun `journey repository findAvailable should find car 2 and dont raise exceptions`() {
        val car1 = CarEntity(1, 4)
        val car2 = CarEntity(2, 5)
        val journey1 = JourneyEntity(1, 3)
        val journey2 = JourneyEntity(2, 4)

        carRepository.save(car1)
        carRepository.save(car2)
        journeyRepository.save(journey1)
        journeyRepository.save(journey2)

        val journey = journeyRepository.findFirstUnassigned()

        assertEquals("should find first unassigned journey", 1L, journey?.id)
    }

}