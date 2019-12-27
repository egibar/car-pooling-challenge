package com.cabify.carpooling.service

import com.cabify.carpooling.data.entities.JourneyEntity
import com.cabify.carpooling.data.entities.toJourney
import com.cabify.carpooling.data.entities.toJourneyEntity
import com.cabify.carpooling.data.repository.JourneyRepository
import com.cabify.carpooling.domain.Journey
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class JourneyService(val journeyRepository: JourneyRepository) {

    fun findById(id: Long): Journey? {
        val journeyEntity: JourneyEntity? = journeyRepository.findByIdOrNull(id)
        journeyEntity?.let {
            return it.toJourney()
        }
        return null
    }

    fun findAll(): List<Journey> {
        return journeyRepository.findAll().map { it.toJourney() }
    }

    fun save(journey: Journey) {
        val journeyEntity = journey.toJourneyEntity()
        journeyRepository.save(journeyEntity)
    }
}