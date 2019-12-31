package com.cabify.carpooling.service

import com.cabify.carpooling.data.entities.toJourney
import com.cabify.carpooling.data.entities.toJourneyEntity
import com.cabify.carpooling.data.repository.CarRepository
import com.cabify.carpooling.data.repository.JourneyRepository
import com.cabify.carpooling.domain.Journey
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

interface JourneyService {
    fun findById(id: Long): Journey?
    fun findAll(): List<Journey>
    fun save(journey: Journey)
}

@Service
class JourneyServiceImpl(val journeyRepository: JourneyRepository, val carRepository: CarRepository) : JourneyService {

    override fun findById(id: Long): Journey? {
        return journeyRepository.findByIdOrNull(id)?.toJourney()
    }

    override fun findAll(): List<Journey> {
        return journeyRepository.findAll().map { it.toJourney() }
    }

    override fun save(journey: Journey) {
        val journeyEntity = journey.toJourneyEntity()
        carRepository.findFirstAvailable(journey.people)?.let { journeyEntity.car = it }
        journeyRepository.save(journeyEntity)
    }
}