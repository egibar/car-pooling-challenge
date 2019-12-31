package com.cabify.carpooling.data

import com.cabify.carpooling.data.repository.CarRepository
import com.cabify.carpooling.data.repository.JourneyRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer(val carRepository: CarRepository, val journeyRepository: JourneyRepository) : CommandLineRunner {
    private val log = LoggerFactory.getLogger(DataInitializer::class.java)

    override fun run(vararg strings: String) {
        // for testing purposes
//        log.info("start data initialization ...")
//        val carList: List<CarEntity> = listOf(CarEntity(1, 4), CarEntity(2, 5), CarEntity(3, 6))
//        carRepository.saveAll(carList)

//        journeyRepository.save(JourneyEntity(1,2))
    }
}