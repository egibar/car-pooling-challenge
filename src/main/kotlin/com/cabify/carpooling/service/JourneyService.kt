package com.cabify.carpooling.service

import com.cabify.carpooling.data.entities.JourneyEntity
import com.cabify.carpooling.data.entities.toJourney
import com.cabify.carpooling.data.entities.toJourneyEntity
import com.cabify.carpooling.data.repository.CarRepository
import com.cabify.carpooling.data.repository.JourneyRepository
import com.cabify.carpooling.domain.Journey
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

interface JourneyService {
    fun findById(id: Long): Journey?
    fun findAll(): List<Journey>
    fun save(journey: Journey)
    /**
     * Deletes a journey by it's id
     * @param id the id of the journey
     * @return True if there was a journey with that Id and it was deleted. False othrwise
     */
    fun deleteById(id: Long): Boolean
}

@Service
class JourneyServiceImpl(val journeyRepository: JourneyRepository, val carRepository: CarRepository) : JourneyService {

    override fun findById(id: Long): Journey? {
        return journeyRepository.findByIdOrNull(id)?.toJourney()
    }

    override fun findAll(): List<Journey> {
        return journeyRepository.findAll().map { it.toJourney() }
    }

    @Transactional
    override fun save(journey: Journey) {
        val journeyEntity = journey.toJourneyEntity()
        carRepository.findFirstAvailable(journey.people)?.let { journeyEntity.car = it }
        journeyRepository.save(journeyEntity)
    }

    @Transactional
    override fun deleteById(id: Long): Boolean {
        val journeyEntity: JourneyEntity? = journeyRepository.findByIdOrNull(id)
        journeyEntity?.let {
            journeyRepository.deleteById(id)
            return true
        }
        return false
    }
}