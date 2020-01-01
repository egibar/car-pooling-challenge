package com.cabify.carpooling.service

import com.cabify.carpooling.data.entities.CarEntity
import com.cabify.carpooling.data.entities.toJourneyEntity
import com.cabify.carpooling.data.repository.CarRepository
import com.cabify.carpooling.data.repository.JourneyRepository
import com.cabify.carpooling.domain.Journey
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.util.AssertionErrors

@SpringBootTest
class JourneyServiceTest {

    @Autowired
    private lateinit var journeyService: JourneyService
    @MockkBean
    private lateinit var carRepository: CarRepository
    @MockkBean
    private lateinit var journeyRepository: JourneyRepository

    @Test
    fun `journey service save should save journey with car assigned and dont raise exceptions`() {
        val carEntity = CarEntity(1, 6)
        val journey = Journey(1, 3)
        val journeyEntity = Journey(1, 3).toJourneyEntity()
        journeyEntity.car = carEntity
        every { carRepository.findFirstAvailable(journey.people) } returns carEntity
        every { journeyRepository.save(journeyEntity) } returns journeyEntity

        val nothing = journeyService.save(journey)
        AssertionErrors.assertEquals("shouldn't return anything", nothing, Unit)
    }

    @Test
    fun `journey service save should save journey without car assigned and dont raise exceptions`() {
        val journey = Journey(1, 3)
        val journeyEntity = Journey(1, 3).toJourneyEntity()
        every { carRepository.findFirstAvailable(journey.people) } returns null
        every { journeyRepository.save(journeyEntity) } returns journeyEntity

        val nothing = journeyService.save(journey)
        AssertionErrors.assertEquals("shouldn't return anything", nothing, Unit)
    }

    @Test
    fun `journey service delete journey`() {
        val journeyId = 1L
        val journeyEntity = Journey(journeyId, 3).toJourneyEntity()
        every { journeyRepository.findByIdOrNull(journeyId) } returns journeyEntity
        every { journeyRepository.deleteById(journeyId) } returns Unit

        val result = journeyService.deleteById(journeyId)
        AssertionErrors.assertEquals("should return true", result, true)
    }

    @Test
    fun `journey service can't delete non existing journey`() {
        val journeyId = 1L
        every { journeyRepository.findByIdOrNull(journeyId) } returns null

        val result = journeyService.deleteById(journeyId)
        AssertionErrors.assertEquals("should return false", result, false)
    }
}