package com.cabify.carpooling.service

import com.cabify.carpooling.data.repository.CarRepository
import com.cabify.carpooling.data.repository.JourneyRepository
import org.springframework.stereotype.Service

interface ResetService {
    fun resetDb()
}

@Service
class ResetServiceImpl(val carRepository: CarRepository, val journeyRepository: JourneyRepository) : ResetService {

    override fun resetDb() {
        carRepository.deleteAll()
        journeyRepository.deleteAll()
    }
}